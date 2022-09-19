package com.upload.service;
public interface FileManagement {

	void upload();

	void download();

	public enum Target {
		HDD, DB

	}

	public static FileManagement getFileManagement(Target target) {

		if (target == Target.HDD) {
			return null;

		}

		else if (target == Target.DB) {
			return null;

		} else {
			throw new RuntimeException("Invalid target");
		}

	}

}
