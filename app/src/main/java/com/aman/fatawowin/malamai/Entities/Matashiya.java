package com.aman.fatawowin.malamai.Entities;

/**
 * Created by Abdulrahman SUlaiman on 3/4/17.
 */

public class Matashiya {
    private String heading;
    private String body;

    public Matashiya(String heading, String body) {
        this.heading = heading;
        this.body = body;
    }

    public Matashiya() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
