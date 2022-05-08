package ru.nsu.fit.oop.game.model.factory;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidRandomEntity;
import ru.nsu.fit.oop.game.model.entity.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

abstract public class Factory {

    protected List<Properties> configs = new ArrayList<>();
    protected Properties configsConfig = new Properties();

    public Factory(String configsConfigName) throws InvalidConfigException {
        String propertyName = null;
        try {
            var stream = this.getClass().getResourceAsStream(propertyName = configsConfigName);
            if (null == stream)
                throw new InvalidConfigException(configsConfigName,this.getClass().getName());
            configsConfig = new Properties();
            Properties nextConfig;
            configsConfig.load(stream);
            for (int i = 0; i < configsConfig.size(); i++) {
                stream = this.getClass().getResourceAsStream(propertyName =
                        configsConfig.getProperty(Integer.valueOf(i).toString()));
                if (null == stream)
                    throw new InvalidConfigException(propertyName,this.getClass().getName());
                nextConfig = new Properties();
                nextConfig.load(stream);
                configs.add(nextConfig);
            }
        } catch (IOException e) {
            throw new InvalidConfigException(propertyName, this.getClass().getSimpleName(), e);
        }
    }

}
