package Collection;

import java.io.Serializable;

public class Color implements Serializable{
	private String color;
	
	public Color(String color){
		this.setColor(color);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString(){
		return getColor();
	}
}
