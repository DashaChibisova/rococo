package guru.qa.rococo.db;

import guru.qa.rococo.config.Config;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public enum Database {
  AUTH("jdbc:postgresql://%s:%d/rococo-auth"),
  ARTIST("jdbc:postgresql://%s:%d/rococo-artist"),
  MUSEUM("jdbc:postgresql://%s:%d/rococo-museum"),
  PAINTING("jdbc:postgresql://%s:%d/rococo-painting"),
  USERDATA("jdbc:postgresql://%s:%d/rococo-userdata");

  private final String url;

  private static final Config cfg = Config.getInstance();

  public String getUrl() {
    return String.format(
        url,
        cfg.jdbcHost(),
        cfg.jdbcPort()
    );
  }

  public String p6spyUrl() {
    return "jdbc:p6spy:" + StringUtils.substringAfter(getUrl(), "jdbc:");
  }
}
