package com.example.skoolworkshop2.domain;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Bank {
    private Context context;

    private String id;
    private String name;
    private VectorDrawable logo;


    public Bank(String id, String name, String svgUrl) {
        this.id = id;
        this.name = name;

        new Thread(() -> {
            if (svgStream(svgUrl) != null) {
                logo = parseSvg(svgStream(svgUrl));
            }
        }).start();
    }

    private InputStream svgStream(String svgUrl) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(svgUrl).openConnection();
            conn.connect();

            return conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private VectorDrawable parseSvg(InputStream svgStream) {
        try {
            XmlPullParserFactory xmlPpf = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPp = xmlPpf.newPullParser();

            xmlPp.setInput(svgStream, null);

            VectorDrawable logo = new VectorDrawable();
            logo.inflate(Resources.getSystem(), xmlPp, null, null);

            return logo;

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public VectorDrawable getLogo() {
        return logo;
    }
}
