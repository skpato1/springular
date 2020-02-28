package com.sifast.convertToJava.tn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XmlToJavaApplication {

	public static void main(String[] args) {
		/*
		 * try { FileWriter myWriter = new FileWriter("/Users/qc/Desktop/User.java");
		 * myWriter.write("package com.sifast.convertToJava.tn;\n");
		 * myWriter.write("@Entity\n");
		 * myWriter.write("public class User implements Serializable {\n");
		 * myWriter.write("private static final long serialVersionUID = 1L;\n");
		 * myWriter.write("}"); myWriter.close();
		 * System.out.println("Successfully wrote to the file."); } catch (IOException
		 * e) { System.out.println("An error occurred."); e.printStackTrace(); }
		 */
		
		SpringApplication.run(XmlToJavaApplication.class, args);
	}
	
	
	
		
	

}






