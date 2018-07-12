package com.wills.carl.gamehub.Util;

import com.google.gson.Gson;
import com.wills.carl.gamehub.model.Game;

import org.json.JSONArray;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParseJson {



    public static ArrayList<Game> parseGames(JSONArray jsonArray) {


        Gson gson = new Gson();
        String data = jsonArray.toString();

        Game games[] = gson.fromJson(data, Game[].class);

        ArrayList<Game> list = new ArrayList<>(games.length);
        Collections.addAll(list, games);
        return list;
    }
}
