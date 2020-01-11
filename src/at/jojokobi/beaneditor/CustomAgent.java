package at.jojokobi.beaneditor;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class CustomAgent {
	
	private static Instrumentation inst = null;
	
	public static void agentmain (final String a, final Instrumentation inst) {
		CustomAgent.inst = inst;
		System.out.println("Agent working");
	}
	
	public static void addToClasspath (File file) {
		try {
			inst.appendToSystemClassLoaderSearch(new JarFile(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
