package com.sifast.springular.framework.business.logic.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.service.IEntityGeneratorService;

@Service
public class EntityGeneratorService implements IEntityGeneratorService {
	
	@Override
	public String functionEquals(String classname)
	{
		String eq=
				"    @Override" +Constants.PATTERN_RETOUR_LIGNE+ 
				"    public boolean equals(Object o) " +Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"        if (this == o) " +Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"            return true"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE+ 
				"       "+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE+  
				"        if (!(o instanceof "+classname+")) "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"            return false"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE+ 
				"        "+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE + 
				"        return id != null && id.equals((("+classname+") o).id)"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"    "+Constants.ACCOLADE_FERMANTE;
		return eq;
	}
	@Override
	public String functionHash()
	{
		String hsh=
				"	 @Override"+Constants.PATTERN_RETOUR_LIGNE + 
				"    public int hashCode() "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE + 
				"        return 31"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"    "+Constants.ACCOLADE_FERMANTE;
		return hsh;
	}
	@Override
	public String functionToString(String classname,List<String> fieldName)
	{
		String str="";
		
		String str1=
				"	  @Override "+Constants.PATTERN_RETOUR_LIGNE + 
				"	  public String toString() "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE + 
				"	  StringBuilder builder = new StringBuilder()"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"	  builder.append(\""+classname+" [id=\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"	  builder.append(id)"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE;
		String str2=
				"        builder.append(\"]\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"		return builder.toString()"+Constants.PATTERN_POINT_VIRGULE;
		for (int j = 0; j < fieldName.size(); j++) {
		
				str=str+
				
				"        builder.append(\", "+fieldName.get(j)+"=\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"        builder.append("+fieldName.get(j)+")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				  
				"";
		}
		return str1+str+str2;
	}
		@Override
		public void generateEntityDynamicWithArrayAngular(String className, List<String> fieldName, List<String> fieldType) {
		if (!"undefined".equals(className)) {
			try {
				String packageName="com.sifast";
				FileWriter myWriter = new FileWriter(Constants.PATTERN_PATH_TO_PACKAGE_MODEL+"/"+ className + ".java");
				myWriter.write("package " + packageName + ".model" +Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write("import javax.persistence.*"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write("import java.io.Serializable"+Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("@Entity"+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("public class " + className + " implements Serializable "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("	private static final long serialVersionUID = 1L"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(
						"	@Id" + Constants.PATTERN_RETOUR_LIGNE+
						"    @GeneratedValue(strategy = GenerationType.IDENTITY)"+Constants.PATTERN_RETOUR_LIGNE + 
						"    private int id"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);

				for (int j = 0; j < fieldName.size(); j++) {
					myWriter.write("	@Column(name = \""+fieldName.get(j)+"\")"+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write("	private " + fieldType.get(j) + " " + fieldName.get(j) + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
				}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(
						"	public int getId() " +Constants.ACCOLADE_OUVRANT+ Constants.PATTERN_RETOUR_LIGNE+
						"        return id"+ Constants.PATTERN_POINT_VIRGULE+ Constants.PATTERN_RETOUR_LIGNE+
						"    "+Constants.ACCOLADE_FERMANTE +Constants.PATTERN_RETOUR_LIGNE+ 
						Constants.PATTERN_RETOUR_LIGNE+ 
						"    public void setId(int id) "+Constants.ACCOLADE_OUVRANT+ Constants.PATTERN_RETOUR_LIGNE+ 
						"        this.id = id"+Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE+ 
						Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				for (int j = 0; j < fieldName.size(); j++) {
					String fakeFieldName = fieldName.get(j).substring(0, 1).toUpperCase() + fieldName.get(j).substring(1);
					myWriter.write(Constants.PATTERN_TABULATION+"public " + fieldType.get(j) + " get" + fakeFieldName + "()"+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.PATTERN_TABULATION+"return " + fieldName.get(j) + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);

					myWriter.write(Constants.PATTERN_TABULATION+"public void set" + fakeFieldName + "(" + fieldType.get(j) + " " + fieldName.get(j) + ")"
							+ Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.PATTERN_TABULATION+"this." + fieldName.get(j) + "=" + fieldName.get(j) + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
				}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(functionEquals(className));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(functionHash());
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(functionToString(className, fieldName));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ACCOLADE_FERMANTE);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			System.out.println("class name null");
		}

	}
		
		
		
		
	

}
