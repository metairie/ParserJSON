package org.fxpart.parser.json;


/**
 * @author stephane
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String sx;
/*        sx =
                "{" +
                        "   \"unicode\": \"x\u0029x\", " +
                        "   \"haha\": '\"test', " +
                        "   \"f\": \"test\", " +
                        "   \"\\tTOTAL\": \"I am \\\"a \\/test\", " +
                        "   \"demo\": 55.23" +
                        "}";
*/
        //  JSONArray with simple JSONObject
        //sx = "[{\"record\": 987, \"name\": \"Community\"}]";

        //  JSONArray with simple JSONObject 3 items
        //sx = "[ {\"record\": 1    , \"name\": \"test\"},    {\"record\": 987, \"name\": \"Community\"}, {\"record\": 999, \"name\": \"Group\"}]";

        //  JSONArray with simple 3 JSONObject imbricated
        //sx = "{\"record\": 1, \"name\": {\"row\": {\"col\": \"colNAME\"}}}";


        //  JSONArray with JSONObject with a JSONArray imbricated (contains 2 JSONObject)
        //sx = "[{\"record\": 1, \"name\": [{\"row\": \"r1\"}, {\"row\": \"r2\"}] }]";

        //  JSONArray 3
        //sx = "[[{\"row\": \"r2\"}, [{\"x\": 9898}]]]";

        // others tests
        //sx = "{\"title\":\"some title\",\"sling:resourceType\":\"foo/bar\",\"jcr:primaryType\":\"nt:unstructured\", , \"name\": {\"row\": {\"col\": \"colNAME\"}}}";
        //sx = "{\"mykey\": \"value\", \"mykey2\": \"value2\"}";
        sx = "{\"title\": \"some title\", \"sling:resourceType\": \"foo/bar\", \"jcr:primaryType\": \"nt:unstructured\", \"jsonobjecthere\": {\"row\": 1}}";
        //sx = "{\"title\": [{\"row\": 1}, {\"row\": 2}]}";

        try {
            JSON j = JSON.parse(sx);
            System.out.println(" origin :" + sx);
            System.out.println(" result :" + j.toString());
        } catch (JSONParsingException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        /*long xl = 589754565465L;
        int xi = 65465;
        double xd = 65455465.56588D;
        Object xn = null;

        for (int i = 0; i < 3000; i++) {
            long nxl = ((Number) xl).longValue();
            System.out.println("XL = " + nxl);

            int nxi = ((Number) xi).intValue();
            System.out.println("Xi = " + nxi);

            double nxd = ((Number) xd).doubleValue();
            System.out.println("Xd = " + nxd);

            Object nxn = (xn);
            System.out.println("Xnull = " + nxn);
        }

        Long Xl = 589754565465L;
        Integer Xi = 65465;
        Double Xd = 65455465.56588D;
        xn = null;
        for (int i = 0; i < 1; i++) {
            Long nxl = (Xl == null ? 0 : xl);
            System.out.println("XL = " + nxl);

            Integer nxi = (Xi == null ? 0 : xi);
            System.out.println("Xi = " + nxi);

            Double nxd = (Xd == null ? 0 : xd);
            System.out.println("Xd = " + nxd);

            Object nxn = (xn == null ? null : xn);
            System.out.println("Xnull = " + nxn);
        }*/

    }

}
