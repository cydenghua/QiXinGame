package com.dcode.qixin.service.impl;

import com.dcode.qixin.mapper.VersionAppMapper;
import com.dcode.qixin.model.VersionApp;
import com.dcode.qixin.service.VersionAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "versionAppService")
public class VersionAppServiceImpl implements VersionAppService {

    @Autowired(required = false)
    private VersionAppMapper versionAppMapper;//这里会报错，但是并不会影响

    @Override
    public VersionApp getLastVersion() {
        return versionAppMapper.getLastVersion();
    }
}
