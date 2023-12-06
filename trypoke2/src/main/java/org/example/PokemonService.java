package org.example;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PokemonService {

    public static Pokemon fetchPokemonData(int pokemonId) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://pokeapi.co/api/v2/pokemon/" + pokemonId);
            String jsonResponse = EntityUtils.toString(httpClient.execute(request).getEntity());

            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, Pokemon.class);
        }
    }

    static class Pokemon {
        String name;
        int id;
        Type[] types;
        Ability[] abilities;
        int height;
        int weight;
        int base_experience;
        Sprites sprites;

        static class Type {
            TypeDetail type;
        }

        static class TypeDetail {
            String name;
        }

        static class Ability {
            AbilityDetail ability;
        }

        static class AbilityDetail {
            String name;
        }

        static class Sprites {
            String front_default; // URL of the sprite
        }

        String getSpriteUrl() {
            return sprites != null ? sprites.front_default : null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(name).append("\n");
            sb.append("ID: ").append(id).append("\n");
            sb.append("Height: ").append(height).append("\n");
            sb.append("Weight: ").append(weight).append("\n");
            sb.append("Base Experience: ").append(base_experience).append("\n");

            sb.append("Types: ");
            for (Type type : types) {
                sb.append(type.type.name).append(" ");
            }
            sb.append("\n");

            sb.append("Abilities: ");
            for (Ability ability : abilities) {
                sb.append(ability.ability.name).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // Remove trailing comma and space
            sb.append("\n");

            return sb.toString();
        }
    }
}