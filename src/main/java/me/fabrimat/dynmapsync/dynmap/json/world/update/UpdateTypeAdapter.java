package me.fabrimat.dynmapsync.dynmap.json.world.update;

import com.google.gson.*;

import java.lang.reflect.Type;

public class UpdateTypeAdapter<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonElement typeName = get(wrapper, "type");
        final Type actualType = typeForName(typeName);
        return context.deserialize(elem, actualType);
    }

    private Type typeForName(final JsonElement typeElem) {
        return switch (typeElem.getAsString()) {
            case "tile" -> Tile.class;
            case "playerquit" -> PlayerQuitMessage.class;
            case "playerjoin" -> PlayerJoinMessage.class;
            case "daynight" -> DayNight.class;
            case "chat" -> ChatMessage.class;
            case "component" -> ComponentMessage.class;
            default -> throw new JsonParseException("invalid type: " + typeElem.getAsString());
        };
    }

    protected JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem;
    }
}
