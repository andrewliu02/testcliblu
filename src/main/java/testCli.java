

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class testCli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		ArrayList<ArrayList<String>> processList= new ArrayList<ArrayList<String>>();
		ArrayList<String> process = new ArrayList<String>();
		ArrayList<String> processName = new ArrayList<String>();
		try {
			pr = rt.exec("bx ic groups");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        String line=null;
        String[] name=null;
        String[] group=null;
        Boolean firstLine = true;
        try {
			while((line=input.readLine()) != null) {
				if(firstLine == false) {
					System.out.println(line);
					processList.add(new ArrayList<String>());
					for(int i = 0;i<line.split("\\s+").length;i++) {
						processList.get(processList.size()-1).add(line.split("\\s+")[i]);
					}
					//processGroup.add(line.split("\\s+")[0] + " ");
					//processName.add(line.split("\\s+")[1]);
				} else {
					firstLine = false;
				}
				
			   // System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("\n  Print all Processes \n");
        for(int i = 0;i<processList.size();i++) {
        	//System.out.println(processGroup.get(i) + processName.get(i));
        	//System.out.println(processList[0] + " " + processList[1]);
        	for(int j =0;j<processList.get(i).size();j++) {
        		System.out.print(processList.get(i).get(j) + " ");
        	}
        	System.out.println("");
        }
        System.out.println("Container Instance Names");
        ArrayList<ArrayList<String>> instanceID = new ArrayList<ArrayList<String>>();
        ArrayList<String> pname = new ArrayList<String>();
        
        for(int ps = 0;ps < processList.size();ps++) {
        	if(!pname.contains(processList.get(ps).get(1))) {
        		pname.add(processList.get(ps).get(1));
        		instanceID.add(getInstanceID(processList.get(ps).get(0),pname.get(pname.size()-1)));            	       
        	}
        }
          
        for(int k = 0;k<instanceID.size();k++) {
        	System.out.println(instanceID.get(k));
        }
        
       // System.out.println("Perform REST Call with : " + processList.get(0).get(0) + "." + instanceID.get(0));
	}
	 public static ArrayList<String> getInstanceID(String id,String name) {
     	ArrayList<String> instanceID = new ArrayList<String>();
     	Runtime rt = Runtime.getRuntime();
		Process pr = null;
		Boolean firstLine = true;
		try {
			pr = rt.exec("bx ic group-instances " + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        String line=null;
        try {
			while((line=input.readLine()) != null) {
				if(firstLine == false) {		
					instanceID.add(id + "." + line.split("\\s+")[0]);
				}
				else { firstLine = false;}
			}	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return instanceID;
	 }
	 
	 public void getMetrics(){
		 // stub for sending metrics via rest
			CredentialsProvider provider = new BasicCredentialsProvider();
	    	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("jay.davis@appdynamics.com", "BigBlueM1x");
			provider.setCredentials(AuthScope.ANY, credentials);
			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).setConnectionTimeToLive(1, TimeUnit.MINUTES).build();
		
			
		 HttpGet get = new HttpGet();
		 get.addHeader("X something","header value");
		 
		 try {
			HttpResponse getResult = client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
