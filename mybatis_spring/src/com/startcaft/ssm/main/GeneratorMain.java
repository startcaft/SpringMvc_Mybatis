package com.startcaft.ssm.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorMain {

	public static void main(String[] args) {
			
		try {
			GeneratorMain sqlMain = new GeneratorMain();
			sqlMain.geneator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void geneator() throws Exception {

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		//指定逆向工程的配置文件
		File configFile = new File("mybatis/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}
}
