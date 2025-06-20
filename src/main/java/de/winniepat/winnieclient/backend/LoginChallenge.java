package de.winniepat.winnieclient.backend;

import de.craftsblock.craftscore.json.Json;
import de.craftsblock.craftscore.json.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class LoginChallenge implements ApiConstants{

    private final HttpClient client;

    private String token;
    private boolean loggedIn = false;

    public LoginChallenge(HttpClient client) {
        this.client = client;
    }

    public void verifyLoginOrLogin() {
        if (isLoggedIn()) return;

        if (hasLoginToken()) {
            verifyMinecraftLicense();
            if (verifyLogin()) return;
            logout();
        }

        Json credentials = verifyMinecraftLicense(true);
        login(credentials);
    }

    public boolean verifyLogin() {
        try {
            HttpRequest request = HttpRequest.newBuilder(buildApiUri("/account/session/check"))
                    .setHeader("Authorization", "Bearer %s".formatted(token))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            Json json = JsonParser.parse(response.body());
            return json.contains("status") && json.getInt("status") == 200;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not verify login", e);
        }
    }

    public void login(Json credentials) {
        credentials.moveTo("id", "login.id");
        credentials.moveTo("type", "login.type");
        credentials.moveTo("secret", "login.token");

        try {
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(credentials.toString());
            HttpRequest request = HttpRequest.newBuilder(buildApiUri("/account/session/login"))
                    .setHeader("Content-Type", "application/json")
                    .POST(body).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            Json json = JsonParser.parse(response.body());

            if (json.getInt("status") != 200) {
                loggedIn = false;
                return;
            }

            token = json.getString("token");

            loggedIn = true;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not log in", e);
        }
    }

    public void logout() {
        try {
            HttpRequest request = HttpRequest.newBuilder(buildApiUri("/account/session/logout"))
                    .setHeader("Authorization", "Bearer %s".formatted(token))
                    .DELETE().build();
            client.send(request, HttpResponse.BodyHandlers.discarding());

            loggedIn = false;
            token = null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not log out", e);
        }
    }

    public void verifyMinecraftLicense() {
        this.verifyMinecraftLicense(false);
    }

    private Json verifyMinecraftLicense(boolean forLogin) {
        Json tokenResponse = requestMinecraftChallenge();

        if (tokenResponse.getInt("status") != 200)
            throw new IllegalStateException("Could not generate token: %s (%s)".formatted(
                    tokenResponse.getString("message"), tokenResponse
            ));

        String token = tokenResponse.getString("token");
        int joinChallengeResponse = performMinecraftChallenge(token);
        if (joinChallengeResponse != 204)
            throw new IllegalStateException("Could not verify session");

        Json challengeResponse = completeMinecraftChallenge(token, forLogin);
        int challengeResposeCode = challengeResponse.getInt("status");
        if (challengeResposeCode != 200)
            throw new IllegalStateException("Could not verify session");

        System.out.println("Session is verified!");
        return challengeResponse.getJson("login");
    }

    private Json requestMinecraftChallenge() {
        try {
            HttpRequest request = HttpRequest.newBuilder(buildApiUri("/minecraft/session/challenge/create"))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return JsonParser.parse(response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not request minecraft challenge", e);
        }
    }

    private int performMinecraftChallenge(String token) {
        try {
            Session session = MinecraftClient.getInstance().getSession();
            String accessToken = session.getAccessToken();
            String profileID = Optional.ofNullable(session.getUuidOrNull())
                    .orElseThrow().toString().replace("-", "");

            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(Json.empty()
                    .set("accessToken", accessToken)
                    .set("selectedProfile", profileID)
                    .set("serverId", token)
                    .toString());

            HttpRequest request = HttpRequest.newBuilder(URI.create("https://sessionserver.mojang.com/session/minecraft/join"))
                    .POST(body).setHeader("Content-Type", "application/json").build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not perform minecraft challenge", e);
        }
    }

    private Json completeMinecraftChallenge(String token, boolean forLogin) {
        try {
            Session session = MinecraftClient.getInstance().getSession();
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(Json.empty()
                    .set("name", session.getUsername())
                    .set("token", token)
                    .toString());

            HttpRequest request = HttpRequest.newBuilder(buildApiUri("/minecraft/session/challenge/complete%s".formatted(
                    forLogin ? "" : "?flag=verify"
            ))).POST(body).setHeader("Content-Type", "application/json").build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return JsonParser.parse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not complete minecraft challenge", e);
        }
    }

    public boolean hasLoginToken() {
        return token != null;
    }

    public boolean isLoggedIn() {
        return loggedIn && hasLoginToken();
    }

}
