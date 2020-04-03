import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;

import java.util.ArrayList;
import java.util.List;

public class Service {
    public static List<ClassResult> Reconice(String API, String Link, String url){
        IamAuthenticator authenticator = new IamAuthenticator("cKCir1H3xw3XfEguERFWasG0g67aPO5HAisCnZ819JVH");
        if(API.length()>0) {
            authenticator = new IamAuthenticator(API);
        }
        VisualRecognition service = new VisualRecognition("2018-03-19", authenticator);

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .url(url)
                .build();
        ClassifiedImages result = service.classify(classifyOptions).execute().getResult();
        service.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/a66fb174-5dc8-4e5e-a617-5c84d435404c");    
        if(Link.length()>0) {
            service.setServiceUrl(Link);
        }

        ClassifiedImages resultado = service.classify(classifyOptions).execute().getResult();
        String classifiers = new String();
        List<ClassResult> classes=resultado.getImages().get(0).getClassifiers().get(0).getClasses();
        for (int i=0; i<classes.size();i++) {
            ClassResult caracteristic=classes.get(i);
            classifiers+=caracteristic.getXClass()+"-"+caracteristic.getScore()+"\n";
        }
        return(classes);
        //System.out.println(resultado.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getXClass());
    }

}
