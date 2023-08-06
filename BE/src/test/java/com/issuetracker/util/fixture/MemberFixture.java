package com.issuetracker.util.fixture;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MemberFixture {

	USER1(1L, "user01@gmail.com", "1234", "유저1", "https://i.imgur.com/wCl8osd.png"),
	USER2(2L, "user02@gmail.com", "1234", "유저2", "https://i.imgur.com/wCl8osd.png"),
	USER3(3L, "user03@gmail.com", "1234", "유저3", "https://i.imgur.com/wCl8osd.png"),
	USER4(4L, "user04@gmail.com", "1234", "유저4", "https://i.imgur.com/wCl8osd.png");

	private final Long id;
	private final String email;
	private final String password;
	private final String nickname;
	private final String proFileImageUrl;

	MemberFixture(Long id, String email, String password, String nickname, String proFileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.proFileImageUrl = proFileImageUrl;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getProFileImageUrl() {
		return proFileImageUrl;
	}

	public static MemberFixture findById(Long id) {
		return Arrays.stream(values())
			.filter(m -> m.id == id)
			.findAny()
			.orElseThrow();
	}

	public static String createInsertSQL() {
		return String.format("INSERT INTO member(email, password, nickname, profile_image_url) VALUES %s", Arrays.stream(values())
			.map(m -> String.format("('%s', '%s', '%s', '%s')",
				m.getEmail(),
				m.getPassword(),
				m.getNickname(),
				m.getProFileImageUrl()))
			.collect(Collectors.joining(", ")));
	}
}
