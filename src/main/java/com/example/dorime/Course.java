package com.example.dorime;

public class Course {
	private int course_id;
	private String title;
	private String image_path;
	private float price;
	private String details;
	private int course_teacher_id;
	private String course_teacher_name;
	

	public Course(int course_id, String title, String image_path, float price, String details, int course_teacher_id, String course_teacher_name) {
		super();
		this.course_id = course_id;
		this.title = title;
		this.image_path = image_path;
		this.price = price;
		this.details = details;
		this.course_teacher_id = course_teacher_id;
		this.course_teacher_name = course_teacher_name;
	}
	
	
	public Course(String title, String image_path, float price, String details, int course_teacher_id,
			String course_teacher_name) {
		super();
		this.title = title;
		this.image_path = image_path;
		this.price = price;
		this.details = details;
		this.course_teacher_id = course_teacher_id;
		this.course_teacher_name = course_teacher_name;
	}





	public int getCourse_id() {
		return course_id;
	}


	public String getTitle() {
		return title;
	}


	public String getImage_path() {
		return image_path;
	}


	public float getPrice() {
		return price;
	}


	public String getDetails() {
		return details;
	}


	public int getCourse_teacher_id() {
		return course_teacher_id;
	}
	
	public String getCourse_teacher_name() {
		return course_teacher_name;
	}

	
	

}
