package garbageboys.garbageman_mk_2.Text;

public class TextObject {//provides a class for each opened text in-game
	String text; 
	float size;
	float x;
	float y;
	int max_height; 
	int width;
	int dupe_i;
	
	public TextObject(String init_text, float init_size, float init_x, float init_y, int init_max_height, int init_width , int init_dupe_i) 
	{
		text = init_text;
		size = init_size;
		x = init_x;
		y = init_y;
		max_height = init_max_height;
		width = init_width;
		dupe_i = init_dupe_i;//this is needed to know the beginning index of each separate opened text, since their handles are all duplicated in the same list 
	}	
}
