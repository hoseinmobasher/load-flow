package xyz.hossein.util;

import java.util.Collection;
import java.util.Stack;

public class Util {
	public static boolean isCollection(Class clazz) {
		Stack<Class> interfaces = new Stack<>();
		interfaces.push(clazz);

		while (!interfaces.empty()) {
			Class candidate = interfaces.pop();

			if (candidate.equals(Collection.class)) {
				interfaces.clear();
				return true;
			}

			for (Class innerCandidate : candidate.getInterfaces()) {
				if (innerCandidate.equals(Collection.class)) {
					interfaces.clear();
					return true;
				}

				interfaces.push(innerCandidate);
			}
		}

		return false;
	}

	public static boolean isInstance(Class clazz, Class candidate) {
		Stack<Class> classes = new Stack<>();
		classes.push(clazz);

		while (!classes.empty()) {
			Class poppedClass = classes.pop();

			if (poppedClass.equals(candidate)) {
				classes.clear();
				return true;
			}

			if (poppedClass.getSuperclass() != null) {
				if (poppedClass.getSuperclass().equals(candidate)) {
					classes.clear();
					return true;
				}

				classes.push(poppedClass.getSuperclass());
			}

			for (Class innerClass : poppedClass.getInterfaces()) {
				if (innerClass.equals(candidate)) {
					classes.clear();
					return true;
				}

				classes.push(innerClass);
			}
		}

		return false;
	}
}
