import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * Created by g2525_000 on 2016/6/6.
 */
public class IIIBed {
    final static String d_0250_ID = "005000000000000000000000";
    final static String d_0550_ID = "055000000000000000000000";
    static String JWTToken = "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI1NzQyODcwNjE4MTMxZ" +
            "TBmYTRiNWEwZDgiLCJpYXQiOjE0NjUxOTc3MDcsImV4cCI6MTQ2NjQwNzMwNywia" +
            "XNzIjoiZ2V0c21hcnR4LmNvbSJ9.N1V5UeXfl-rHJgr24TlsHyv6fWwQcjY7FTGan" +
            "B7KDsw";

    public static void main(String[] args) {
        receiveToken();
        while (true) System.out.println(getLatestData(d_0550_ID));
        //System.out.println(getPeriodData(d_0550_ID, "1464919670000", "1464919692619"));

    }

    public static void receiveToken() {
        String body = "";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post("https://getsmartx.com:3000/api/1/login")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body("{\"username\":\"ntu_cs\", \"password\":\"oH392tFEMo\"}").asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        /*String response = HttpRequest.post("https://getsmartx.com:3000/api/1/login")
                .header("Content-Type","application/json;charset=UTF-8")
                .basic("ntu_cs", "oH392tFEMo").body();*/


        JSONObject res = (JSONObject) response.getBody().getObject().get("res");
        JWTToken = "JWT " + res.get("token");
        //System.out.println(JWTToken);

    }

    public static String getLatestData(String id) {

        String response = null;
        try {
            response = Unirest.get("https://getsmartx.com:3000/" +
                    "api/1/streams/latest/" + id).header("Authorization",
                    JWTToken).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getPeriodData(String id, String fromTime, String toTime) {
        String response = null;
        try {
            response = Unirest.get("https://getsmartx.com:3000/api/1/streams/listByDevice/" + id)
                    .queryString("from_time", fromTime).queryString("to_time", toTime)
                    .header("Authorization", JWTToken).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        /*String response = HttpRequest.get("https://getsmartx.com:3000/api/1/streams/listByDevice/"
                + id, true, "from_time", fromTime, "to_time", toTime).header("Authorization",
                JWTToken).body();*/
        return response;
    }

}
