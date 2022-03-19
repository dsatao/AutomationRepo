package utils;

public enum WaitEnum {

	MIN_TIME(10), MAX_TIME(60), AVG_TIME(20);

	public static int getResource(String resourceType) {
		return WaitEnum.valueOf(resourceType).getResource();
	}

	private int resource;

	WaitEnum(int resource) {
		this.resource = resource;
	}

	public int getResource() {
		return resource;
	}
}
