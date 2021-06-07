package codeSmellsExample;

import beans.ClassBean;
import beans.MethodBean;
import beans.PackageBean;
import utilities.FolderToJavaProjectConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

public class CodeSmellDetector {

    public static void main(String args[]) {

        // Path to the directory containing all the projects under analysis
        String pathToDirectory = "/Users/apple/Desktop/SwD/Project/code";
        File experimentDirectory = new File(pathToDirectory);

        // Declaring Class-level code smell objects.
        ClassDataShouldBePrivateRule cdsbp = new ClassDataShouldBePrivateRule();
        ComplexClassRule complexClass = new ComplexClassRule();
        FunctionalDecompositionRule functionalDecomposition = new FunctionalDecompositionRule();
        GodClassRule godClass = new GodClassRule();
        SpaghettiCodeRule spaghettiCode = new SpaghettiCodeRule();
        LongMethodRule longMethod = new LongMethodRule();

        // The following rules are quite low for detecting smelly code components.
        // MisplacedClassRule misplacedClass = new MisplacedClassRule();
        // FeatureEnvyRule featureEnvy = new FeatureEnvyRule();

        for (File project : experimentDirectory.listFiles()) {
            try {
                // Method to convert a directory into a set of java packages.
                Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

                // Used for making CSV file
                try (PrintWriter writer = new PrintWriter(new File("/Users/apple/Desktop/SwD/Project/code-smells/codesmells.csv"))) {

                    StringBuilder sb = new StringBuilder();

                    for (PackageBean packageBean : packages) {
                        for (ClassBean classBean : packageBean.getClasses()) {

                            // How to call methods for computing code smell detection.
                            // The currently implemented detector is DECOR (http://www.irisa.fr/triskell/publis/2009/Moha09d.pdf)
                            boolean isClassDataShouldBePrivate = cdsbp.isClassDataShouldBePrivate(classBean);
                            boolean isComplexClass = complexClass.isComplexClass(classBean);
                            boolean isFunctionalDecomposition = functionalDecomposition.isFunctionalDecomposition(classBean);
                            boolean isGodClass = godClass.isGodClass(classBean);
                            boolean isSpaghettiCode = spaghettiCode.isSpaghettiCode(classBean);

                            //Code-smell data append here in the StringBuilder variable
                            sb.append(classBean.getBelongingPackage() + "." + classBean.getName() + ",");
                            sb.append(isClassDataShouldBePrivate + ",");
                            sb.append(isComplexClass + ",");
                            sb.append(isFunctionalDecomposition + ",");
                            sb.append(isGodClass + ",");
                            sb.append(isSpaghettiCode + ",");
                            sb.append('\n');


                        /*System.out.println("Class: " + classBean.getBelongingPackage()
                                + "." + classBean.getName() + "\n"
                                + "		ClassDataShouldBePrivate: " + isClassDataShouldBePrivate + "\n"
                                + "		ComplexClass: " + isComplexClass + "\n"
                                + "		FunctionalDecomposition: " + isFunctionalDecomposition + "\n"
                                + "		GodClass: " + isGodClass + "\n"
                                + "		SpaghettiCode: " + isSpaghettiCode);

                        for(MethodBean methodBean: classBean.getMethods()) {
                            boolean isLongMethod = longMethod.isLongMethod(methodBean);

                            System.out.println("Method: " + methodBean.getName() + "\n"
                                    + "     LongMethod: " + isLongMethod);
                        }*/
                        }
                    }

                    // String data converted to CSV file
                    writer.write(sb.toString());
                    System.out.println("done");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

}