package se.narstrom.imageio.pgm;

import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class PGMImageReaderSpi extends ImageReaderSpi {
	public PGMImageReaderSpi() {
		super("Rickard Närström", "1.1",						/* vender, version */
				new String[] { "Portable Graymap" },			/* format names */
				new String[] { "pgm" },							/* filename extensions */
				new String[] { "image/x-portable-graymap" },	/* MIME types */
				PGMImageReader.class.getCanonicalName(),		/* reader class name */
				new Class<?>[] { ImageInputStream.class },		/* supported input types */
				new String[] { PGMImageWriterSpi.class.getCanonicalName() },		/* writer spi class names */
				false, null, null, null, null,		/* stream metadata */
				false, null, null, null, null		/* image metadata */
				);
	}

	@Override
	public boolean canDecodeInput(Object source) throws IOException {
		if(!(source instanceof ImageInputStream))
			return false;
		ImageInputStream in = (ImageInputStream)source;
		in.mark();
		boolean ret = in.read() == 'P' && in.read() == '2'; // Magic number
		in.reset();
		return ret;
	}

	@Override
	public ImageReader createReaderInstance(Object extension) throws IOException {
		return new PGMImageReader(this);
	}

	@Override
	public String getDescription(Locale locale) {
		return "Portable Graymap image reader";
	}
}
