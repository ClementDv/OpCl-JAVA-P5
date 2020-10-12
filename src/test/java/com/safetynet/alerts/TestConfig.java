package com.safetynet.alerts;

import com.safetynet.alerts.repository.DataFileAccess;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {DataFileAccess.class})
public class TestConfig {
}
