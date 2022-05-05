package hw1;

public class Main {
	public static void main(String[] args) {

		MyFreeList<String> names = new MyFreeList<String>();
		names.add("Jessica");
		names.add("Judith");
		names.add("William");
		names.add("Henry");
		names.add("Jack");

		System.out.println("size:" + names.size());

		System.out.println("Some items:");
		System.out.println(names.get(2));
		System.out.println(names.get(4));

		System.out.println("-------All List---------");
		for (String string : names) {
			System.out.println(string);
		}
		System.out.println("remove:");
		System.out.println(names.remove(2));

		System.out.println("-------Remaining List---------");
		for (String string : names) {
			System.out.println(string);
		}

		System.out.println("size:" + names.size());

		System.out.println("Contains:" + names.contains("Henry"));
		System.out.println("Index of Henry:" + names.indexOf("Henry"));
		names.add(names.indexOf("Henry"), "Gary");
		System.out.println("Index of Gary:" + names.indexOf("Gary"));
		names.clear();
		System.out.println("cleared, size:" + names.size());
	}
}