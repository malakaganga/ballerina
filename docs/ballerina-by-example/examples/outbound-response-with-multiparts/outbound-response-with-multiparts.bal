import ballerina.net.http;
import ballerina.mime;
import ballerina.file;

@http:configuration {port:9092}
service<http> multiparts {
    @http:resourceConfig {
        methods:["GET"],
        path:"/encode_out_response"
    }
    resource multipartSender (http:Connection conn, http:InRequest req) {

        //Create an enclosing entity to hold child parts.
        mime:Entity parentPart = {};
        mime:MediaType mixedContentType = mime:getMediaType(mime:MULTIPART_MIXED);
        parentPart.contentType = mixedContentType;

        //Create a child part with json content.
        mime:Entity childPart1 = {};
        mime:MediaType contentTypeOfJsonPart = mime:getMediaType(mime:APPLICATION_JSON);
        childPart1.contentType = contentTypeOfJsonPart;
        childPart1.setJson({"name":"wso2"});

        //Create another child part with a file.
        mime:Entity childPart2 = {};
        mime:MediaType contentTypeOfFilePart = mime:getMediaType(mime:TEXT_XML);
        childPart2.contentType = contentTypeOfFilePart;
        file:File fileHandler = {path:"/home/user/Downloads/test.xml"};
        childPart2.setFileAsEntityBody(fileHandler);

        //Create an array to hold child parts.
        mime:Entity[] childParts = [childPart1, childPart2];

        //Set the child parts to parent part.
        parentPart.setBodyParts(childParts);

        //Create an array to hold the parent part and set it to response.
        mime:Entity[] immediatePartsToResponse = [parentPart];
        http:OutResponse outResponse = {};
        outResponse.setMultiparts(immediatePartsToResponse, mime:MULTIPART_FORM_DATA);

        _ = conn.respond(outResponse);
    }
}
