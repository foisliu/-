import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfByte;
import org.opencv.objdetect.CascadeClassifier;


public class Main {
    public static void main(String[] args) {
        // Load the input image
        Mat inputMat = Imgcodecs.imread("path/to/input/image.jpg");

        // Load the cascade classifier for detecting kiwi fruit
        CascadeClassifier kiwiDetector = new CascadeClassifier();
        kiwiDetector.load("path/to/kiwi/cascade/classifier.xml");

        // Detect kiwi fruit in the input image
        MatOfRect kiwiDetections = new MatOfRect();
        kiwiDetector.detectMultiScale(inputMat, kiwiDetections);

        // Draw a rectangle around each detected kiwi fruit
        for (Rect rect : kiwiDetections.toArray()) {
            Imgproc.rectangle(inputMat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }

        // Save the output image
        Imgcodecs.imwrite("path/to/output/image.jpg", inputMat);
    }
}

public class MainActivity extends AppCompatActivity {

    private void staticLoadCVLibraries() {
        boolean load = OpenCVLoader.initDebug();
        if (load) {
            Log.i("CV", "Open CV Libraries loaded...");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ImageView and TextView elements by their IDs
        ImageView picture2 = (ImageView) findViewById(R.id.picture2);
        TextView tv2 = (TextView) findViewById(R.id.tv2);

        // Create button listeners and assign them to the corresponding buttons
        Button btnSavePhoto = (Button) findViewById(R.id.btnSavePhoto);
        Button xcxq = (Button) findViewById(R.id.xcxq);
        Button button4 = (Button) findViewById(R.id.button4);

        ButtonPhotoListener3 btnlistener3 = new ButtonPhotoListener3(picture2, tv2);
        button4.setOnClickListener(btnlistener3);

        ButtonPhotoListener2 btnlistener2 = new ButtonPhotoListener2(xcxq, hy, xx, hwd, MainActivity.this);
        xcxq.setOnClickListener(btnlistener2);

        ButtonPhotoListener btnlistener = new ButtonPhotoListener(ivShowPicture, picture2, tv2);
        btnSavePhoto.setOnClickListener(btnlistener);

        // Find the RadioButton elements by their IDs
        RadioButton xx = (RadioButton) findViewById(R.id.xx);
        RadioButton hy = (RadioButton) findViewById(R.id.hy);
        RadioButton hwd = (RadioButton) findViewById(R.id.hwd);
    }

    class ButtonPhotoListener2 implements OnClickListener {
        private Button xcxq;
        private RadioButton hy;
        private RadioButton xx;
        private RadioButton hwd;
        private Context context;

        public ButtonPhotoListener2(Button xcxq, RadioButton hy, RadioButton xx, RadioButton hwd, Context context) {
            this.xcxq = xcxq;
            this.hy = hy;
            this.xx = xx;
            this.hwd = hwd;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == xcxq.getId()) {
                if (hy.isChecked()) {
                    // do something
                } else if (xx.isChecked()) {
                    // do something
                } else if (hwd.isChecked()) {
                    // do something
                }
            }
        }
    }

    class ButtonPhotoListener3 implements OnClickListener {
        private ImageView picture2;
        private TextView tv2;

        public ButtonPhotoListener3(ImageView picture2, TextView tv2) {
            this.picture2 = picture2;
            this.tv2 = tv2;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button4) {
                ivShowPicture.setImageDrawable(null);
                picture2.setImageDrawable(null);
                tv2.setText("");
                bul = false;
            }
        }
    }

    class ButtonPhotoListener implements OnClickListener {
        private ImageView ivShowPicture;
        private ImageView picture2;
        private TextView tv2;

        public ButtonPhotoListener(ImageView ivShowPicture, ImageView picture2, TextView tv2) {
            this.ivShowPicture = ivShowPicture;
            this.picture2 = picture2;
            this.tv2 = tv2;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnSavePhoto) {
                if (bul) {
                    savePhoto();
                    processImage();
                } else {
                    Toast.makeText(MainActivity.this, "请先选择猕猴桃品种再进行膨大判断！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void processImage() {
        Utils.bitmapToMat(inputBitmap, inputMat);
        Imgproc.cvtColor(inputMat, hsvMat, Imgproc.COLOR_BGR2HSV);
        Imgproc.threshold(hsvMat, binaryMat, 127, 255, Imgproc.THRESH_OTSU);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double[] bgra = mulMat.get(row, col);
                double pixelValue = (double) (bgra[0] & 0xff) / 255D;
                if (pixelValue > 0) {
                    if (pixelValue <= 0.2) {
                        bgra[0] = (byte) (255);
                    } else if (pixelValue >= 0.6) {
                        bgra[0] = (byte) (3);
                        sum += pixelValue;
                        values[key] = pixelValue;
                        key++;
                    } else {
                        pixelValue = 1 - (Math.pow((pixelValue - 0.2) / 0.4, 0.166) * 0.99 + 0.01);
                        pixelValue *= 255;
                        bgra[0] = (byte) (pixelValue);
                    }
                }
                mulMat.put(row, col, bgra);
            }
        }
        double avg = sum / key;
        tv2.setText("平均值：" + avg);
        Imgproc.cvtColor(mulMat, outputMat, Imgproc.COLOR_BGRA2BGR);
        Utils.matToBitmap(outputMat, outputBitmap);
        picture2.setImageBitmap(outputBitmap);
        double mean = sum / (key + 1);
        for (int k = 0; k < key; k++) {
            ssgr += Math.pow((values[k] - mean), 2);
        }
        double sd = Math.sqrt(ssgr / (key + 1));
        double thresholdValue = mean + 1.5 * sd;
        Imgproc.threshold(mulMat, outputMat, thresholdValue, 255, Imgproc.THRESH_BINARY);

        // Define the 'mul' variable and initialize it with the appropriate value
        Mat mul = outputMat.clone();

        Imgproc.findContours(mul.clone(), contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // Define the 'i' variable and initialize it with the appropriate value
        int i = 0;

        double contourArea = Imgproc.contourArea(contours.get(i));
        Imgproc.fillPoly(mul, contours, fillColor, 8, 0, new Point(0, 0));

        List<Point> points = new ArrayList<>();
        for (MatOfPoint matOfPoint : ltqylist4) {
            points.addAll(matOfPoint.toList());
        }
        MatOfPoint2f point2f = new MatOfPoint2f();
        point2f.fromList(points);
        RotatedRect rRect = Imgproc.minAreaRect(point2f);
        Point[] vertices = new Point[4];
        rRect.points(vertices);
        for (int l = 0; l < 4; l++) {
            Core.line(mul, vertices[l], vertices[(l + 1) % 4], new Scalar(255, 0, 0, 0), 5);
        }
    }
}
        