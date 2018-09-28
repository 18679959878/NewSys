package com.sys.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CharacterFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		filterChain.doFilter(new HttpServletRequestWrapper(request) {

			@Override
			public String getParameter(String name) {
				// ����ֵ֮ǰ �Ƚ��й���
				return filterDangerString(super.getParameter(name));
			}

			@Override
			public String[] getParameterValues(String name) {
				// ����ֵ֮ǰ �Ƚ��й���
				String[] values = super.getParameterValues(name);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						values[i] = filterDangerString(values[i]);
					}
				}
				return values;
			}
		}, response);
	}

	public String filterDangerString(String value) {
		if (value == null) {
			return null;
		}

		// �����Լ�ʵ���������

		// value = value.replaceAll("", null);
		if (value.equals("")) {
			return null;
		}
		return value;
	}

}
