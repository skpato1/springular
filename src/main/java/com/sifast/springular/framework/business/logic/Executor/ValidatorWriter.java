package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class ValidatorWriter {

    public void generateValidators(Project project) {
        project.getEntities().stream().forEach(ent -> {
            try {
                String fileValidator = ent.getNameEntity().concat("Validator");
                FileWriter myWriter = writeImportsAndStructureOfClassInValidators(project, ent, fileValidator);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private FileWriter writeImportsAndStructureOfClassInValidators(Project project, BuisnessLogicEntity ent, String fileValidator) throws IOException {
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_VALIDATOR).concat(fileValidator).concat(".java"));

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_VALIDAORS.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_COMPONENT);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_COMPONENT);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileValidator).concat(Constants.EXTENDS).concat("Validator ").concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();
    }

}
