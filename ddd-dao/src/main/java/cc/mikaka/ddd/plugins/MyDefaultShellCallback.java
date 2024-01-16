package cc.mikaka.ddd.plugins;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 生成完后的回调，主要是新旧代买的merge
 * <p>
 * TODO 应该按照注解来判断是否merge相应的方法和xml
 *
 * @see org.mybatis.generator.config.MergeConstants
 */
public class MyDefaultShellCallback extends DefaultShellCallback {


    public MyDefaultShellCallback(boolean overwrite) {
        super(overwrite);
    }

    @Override
    public boolean isMergeSupported() {
        return true;
    }

    @Override
    public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) throws ShellException {
        String result = newFileSource;
        Charset charset = StandardCharsets.UTF_8;
        if (StringUtils.isNotEmpty(fileEncoding)) {
            charset = Charset.forName(fileEncoding);
        }
        if (isClassInterface(existingFile, charset)) {
            result = readFile(existingFile, charset);
        }
        return result;
    }

    private String readFile(File file, Charset charset) {
        StringBuffer sb = new StringBuffer();
        try {
            List<String> lines = Files.readLines(file, charset);
            lines.forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private boolean isClassInterface(File file, Charset charset) {
        boolean result = false;
        List<String> lines = null;
        try {
            lines = Files.readLines(file, charset);
        } catch (IOException e) {
            return result;
        }
        for (String line : lines) {
            if (line.contains("interface")) {
                result = true;
                break;
            }
        }
        return result;
    }
}