package com.dtusystem.protocol;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    /**
     * 反序列化
     * */
    <T> T deserialize(Class<T> clazz, byte[] objectByte);

    /**
     * 序列化
     * */
    <T> byte[] serialize(T obj);


    enum Algorithm implements Serializer{
        Java{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] objectByte) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(objectByte));
                    return (T) ois.readObject();
                } catch (IOException |ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败", e);
                }
            }

            @Override
            public <T> byte[] serialize(T obj) {
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    return bos.toByteArray();
                }catch (IOException e){
                    throw new RuntimeException("序列化失败", e);
                }
            }
        },
        Json{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();
                String json = new String(bytes, StandardCharsets.UTF_8);
                return gson.fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();
                String json = gson.toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }
    }

    class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                String str = json.getAsString();
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override             //   String.class
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
            // class -> json
            return new JsonPrimitive(src.getName());
        }
    }
}
