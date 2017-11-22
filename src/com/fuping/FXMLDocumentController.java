package com.fuping;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.internal.InputElement;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import com.teamdev.jxbrowser.chromium.javafx.DefaultNetworkDelegate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.http.util.ByteArrayBuffer;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class FXMLDocumentController implements Initializable {


    @FXML
    private TextField id_baseurlinput;

    @FXML
    private Button id_crack;

    @FXML
    private RadioButton id_userbyid;

    @FXML
    private ToggleGroup usernamegroup;

    @FXML
    private RadioButton id_userbyname;

    @FXML
    private TextField id_userinput;

    @FXML
    private RadioButton id_passbyid;

    @FXML
    private ToggleGroup passwordgroup;

    @FXML
    private RadioButton id_passbyname;

    @FXML
    private TextField id_passinput;

    @FXML
    private RadioButton id_captchabyid;

    @FXML
    private ToggleGroup captchainputgroup;

    @FXML
    private RadioButton id_captchabyname;

    @FXML
    private TextField id_captchainput;

    @FXML
    private ToggleGroup captchagroup;

    @FXML
    private TextField id_chatchaurlinput;

    @FXML
    private TextField softid;

    @FXML
    private TextField softkey;

    @FXML
    private ComboBox<Integer> timeout;

    @FXML
    private Button info;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField typeid;

    @FXML
    private Hyperlink id_typehelp;

    @FXML
    private TextArea id_output;

    @FXML
    private RadioButton id_submitbyid;

    @FXML
    private ToggleGroup submitgroup;

    @FXML
    private RadioButton id_submitbyname;

    @FXML
    private TextField id_submitinput;

    @FXML
    private TextField id_keyword;

    @FXML
    private ComboBox<Integer> id_interval;

    @FXML
    private CheckBox id_havecaptcha;

    @FXML
    private HBox id_hbox11;

    @FXML
    private HBox id_hbox22;

    @FXML
    private HBox id_hbox33;

    @FXML
    private HBox id_hbox44;

    @FXML
    private HBox id_hbox55;

    @FXML
    private RadioButton id_userbyclass;

    @FXML
    private RadioButton id_passbyclass;

    @FXML
    private RadioButton id_submitbyclass;

    @FXML
    private RadioButton id_captchabyclass;

    @FXML
    private CheckBox id_showbrowser;

    @FXML
    private TextArea id_requestarea;

    @FXML
    private HBox id_hbox221;

    @FXML
    private TextField id_chatchaurlinput2;

    @FXML
    private TextField id_keyword2;

    @FXML
    private Button id_setusernamepos;

    @FXML
    private Button id_setpasswordpos;

    @FXML
    private CheckBox id_havecaptcha2;

    @FXML
    private Button id_setcaptchapos;

    @FXML
    private ComboBox<Integer> id_threads2;

    @FXML
    private ComboBox<Integer> id_timeout2;

    @FXML
    private TextArea id_outputarea2;

    @FXML
    private TabPane id_tabpane;

    @FXML
    private Tab id_tab1;

    @FXML
    private Tab id_tab2;

    @FXML
    private RadioButton yzm_yunRadioBtn;

    @FXML
    private RadioButton yzm_localRadioBtn;

    @FXML
    private ToggleGroup yzmgroup;


    private byte[] captchadata;
    private Stage primaryStage;
    private LinkedBlockingQueue<UserPassPair> queue;
    private Boolean isstopsendcrack;

    private String yzmText = null;

    private List<Cookie> cookies = null;

    public void initialize(URL url, ResourceBundle rb) {
        this.id_requestarea.setPromptText(
                "POST /login.do\r\nHost: 192.168.0.123:8080\r\nUser-Agent: User-Agent:Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\r\n\r\nusername=$username$&passwd=$$password&captcha=$captcha$");

        ObservableList to = FXCollections.observableArrayList(new Integer[]{Integer.valueOf(30), Integer.valueOf(50), Integer.valueOf(60), Integer.valueOf(70), Integer.valueOf(80), Integer.valueOf(90), Integer.valueOf(100)});
        this.timeout.setItems(to);

        ObservableList interval = FXCollections.observableArrayList(new Integer[]{Integer.valueOf(0), Integer.valueOf(500), Integer.valueOf(1000), Integer.valueOf(2000), Integer.valueOf(3000), Integer.valueOf(5000), Integer.valueOf(8000),
                Integer.valueOf(10000), Integer.valueOf(15000), Integer.valueOf(20000)});
        this.id_interval.setItems(interval);

        ObservableList timeout2 = FXCollections.observableArrayList(new Integer[]{Integer.valueOf(1000), Integer.valueOf(2000), Integer.valueOf(3000), Integer.valueOf(4000), Integer.valueOf(5000), Integer.valueOf(8000), Integer.valueOf(10000)});

        this.id_timeout2.setItems(timeout2);

        ObservableList threads = FXCollections.observableArrayList(new Integer[]{Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(10), Integer.valueOf(20), Integer.valueOf(30), Integer.valueOf(50), Integer.valueOf(60), Integer.valueOf(70), Integer.valueOf(80), Integer.valueOf(90),
                Integer.valueOf(100)});

        this.id_threads2.setItems(threads);
    }

    @FXML
    private void queryinfo(ActionEvent event) {
        String result = "";
        result = YunSu.getInfo(this.username.getText().trim(), this.password.getText().trim());
        this.id_output.appendText(result);
    }


    @FXML
    private void startcrack(ActionEvent event) {
        String baseurl = this.id_baseurlinput.getText();

        if (baseurl.equals("")) {
            new Alert(Alert.AlertType.NONE, "请输入登录页面URL", new ButtonType[]{ButtonType.CLOSE}).show();
            return;
        }

        if (!baseurl.startsWith("http")) {
            baseurl = "http://" + baseurl;
        }
        String loginurl = baseurl;

        String username1 = this.username.getText().trim();
        String password1 = this.password.getText().trim();
        String softid1 = this.softid.getText().trim();
        String softkey1 = this.softkey.getText().trim();
        String typeid1 = this.typeid.getText().trim();
        String captchaurlinput = this.id_chatchaurlinput.getText().trim();
        String submitinput = this.id_submitinput.getText().trim();

        Integer timeoutint = (Integer) this.timeout.getValue();
        String timeout1 = null;
        if (timeoutint == null)
            timeout1 = "60";
        else {
            timeout1 = timeoutint.toString();
        }

        YunSuConfig yunSuConfig = new YunSuConfig(username1, password1, softid1, softkey1, typeid1, timeout1);

        if (this.id_tab1.isSelected()) {
            if ((this.id_havecaptcha.isSelected()) && ((username1.equals("")) || (password1.equals("")))) {
                new Alert(Alert.AlertType.NONE, "云速账号密码不能为空", new ButtonType[]{ButtonType.CLOSE});
                return;
            }

            String userinput = this.id_userinput.getText();
            if (userinput.equals("")) {
                this.id_userinput.requestFocus();
                return;
            }
            String passinput = this.id_passinput.getText();
            if (passinput.equals("")) {
                this.id_passinput.requestFocus();
                return;
            }
            String captchainput = this.id_captchainput.getText();
            if (captchainput.equals("")) {
                this.id_userinput.requestFocus();
                return;
            }
            String chatchaurlinput = this.id_chatchaurlinput.getText();
            if (chatchaurlinput.equals("")) {
                this.id_chatchaurlinput.requestFocus();
                return;
            }

            String to = timeout1;
            this.primaryStage = new Stage();
            Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
            browser.getContext().getNetworkService().setNetworkDelegate(new MyNetworkDelegate(captchaurlinput));

            BrowserView view = new BrowserView(browser);
            BorderPane borderPane = new BorderPane(view);

            ProgressIndicator progressIndicator = new ProgressIndicator(1.0D);
            progressIndicator.setPrefHeight(30.0D);
            progressIndicator.setPrefWidth(30.0D);
            TextField urlinput = new TextField();
            HBox Hbox = new HBox(2.0D, new Node[]{progressIndicator, urlinput});
            HBox.setHgrow(urlinput, Priority.ALWAYS);
            Hbox.setAlignment(Pos.CENTER_LEFT);

            borderPane.setTop(Hbox);

            Scene scene = new Scene(borderPane, 800.0D, 700.0D);
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("请勿操作浏览器页面");
            if (this.id_showbrowser.isSelected()) {
                this.primaryStage.show();
            }

            browser.addLoadListener(new LoadAdapter() {
                public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
                    if (event.isMainFrame())
                        Platform.runLater(new Runnable() {
                            public void run() {
                                urlinput.setText(event.getURL());
                            }
                        });
                }

                public void onStartLoadingFrame(StartLoadingEvent paramStartLoadingEvent) {
                    if (paramStartLoadingEvent.isMainFrame())
                        Platform.runLater(new Runnable() {
                            public void run() {
                                progressIndicator.setProgress(-1.0D);
                                FXMLDocumentController.this.id_output.appendText("开始访问URL:" + paramStartLoadingEvent.getValidatedURL() + "\n");
                            }
                        });
                    super.onStartLoadingFrame(paramStartLoadingEvent);
                }

                public void onFailLoadingFrame(FailLoadingEvent paramFailLoadingEvent) {
                    if (paramFailLoadingEvent.isMainFrame())
                        Platform.runLater(new Runnable() {
                            public void run() {
                                progressIndicator.setProgress(1.0D);
                            }
                        });
                    super.onFailLoadingFrame(paramFailLoadingEvent);
                }

                public void onFinishLoadingFrame(FinishLoadingEvent paramFinishLoadingEvent) {
                    if (paramFinishLoadingEvent.isMainFrame()) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                progressIndicator.setProgress(1.0D);
                            }
                        });
                    }
                    super.onFinishLoadingFrame(paramFinishLoadingEvent);
                }
            });
            PopupHandler popupHandler = new PopupHandler() {
                public PopupContainer handlePopup(PopupParams paramPopupParams) {
                    paramPopupParams.getParent().loadURL(paramPopupParams.getURL());
                    return null;
                }
            };
            browser.setPopupHandler(popupHandler);

            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    browser.dispose();
                }
            });
            browser.setDialogHandler(new MyDialogHandler(view));

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Integer interval = (Integer) FXMLDocumentController.this.id_interval.getValue();
                        if (FXMLDocumentController.this.id_interval.getValue() == null) {
                            interval = Integer.valueOf(1500);
                        }
                        FileReader fruser = new FileReader("dict" + File.separator + "username.txt");
                        BufferedReader bruser = new BufferedReader(fruser);
                        String u;
                        while ((u = bruser.readLine()) != null) {
                            FileReader frpass = new FileReader("dict" + File.separator + "password.txt");
                            BufferedReader brpass = new BufferedReader(frpass);
                            String p;
                            while ((p = brpass.readLine()) != null) {
                                String uu = u;
                                String pp = p;
                                FXMLDocumentController.this.captchadata = null;
                                Platform.runLater(new Runnable() {
                                    public void run() {
                                        FXMLDocumentController.this.id_output.appendText("正在测试用户名:" + uu + " 密码:" + pp + "\r\n");
                                    }
                                });
                                try {

                                    Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
                                                public void invoke(Browser browser) {
                                                    browser.loadURL(loginurl);
                                                }
                                            }
                                            , 120);
                                } catch (IllegalStateException e) {
                                    String m = e.getMessage();
                                    System.out.println(m);
                                    if (m.contains("Channel is already closed")) {
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                FXMLDocumentController.this.id_output.appendText("停止测试\n");
                                            }
                                        });
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Platform.runLater(new Runnable() {
                                        public void run() {
                                            FXMLDocumentController.this.id_output.appendText("访问超时\r\n");
                                        }
                                    });
                                    continue;
                                }
                                if (interval.intValue() != 0) {
                                    Thread.sleep(interval.intValue() / 2);
                                }
                                DOMDocument doc = browser.getDocument();

                                try {
                                    if (FXMLDocumentController.this.id_userbyid.isSelected()) {
                                        InputElement ele = (InputElement) doc.findElement(By.id(userinput));
                                        ele.setValue(u);
                                    } else if (FXMLDocumentController.this.id_userbyname.isSelected()) {
                                        InputElement ele = (InputElement) doc.findElement(By.name(userinput));
                                        ele.setValue(u);
                                    } else {
                                        InputElement ele = (InputElement) doc.findElement(By.className(userinput));
                                        ele.setValue(u);
                                    }

                                    if (FXMLDocumentController.this.id_passbyid.isSelected()) {
                                        InputElement ele = (InputElement) doc.findElement(By.id(passinput));
                                        ele.setValue(p);
                                    } else if (FXMLDocumentController.this.id_passbyname.isSelected()) {
                                        InputElement ele = (InputElement) doc.findElement(By.name(passinput));
                                        ele.setValue(p);
                                    } else {
                                        InputElement ele = (InputElement) doc.findElement(By.className(passinput));
                                        ele.setValue(p);
                                    }
                                } catch (IllegalStateException e) {
                                    String m = e.getMessage();
                                    System.out.println(m);
                                    if (m.contains("Channel is already closed")) {
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                FXMLDocumentController.this.id_output.appendText("IllegalStateException异常,无法获取用户名或密码输入框按钮,停止测试\n");
                                            }
                                        });
                                        break;
                                    }
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    Platform.runLater(new Runnable() {
                                        public void run() {
                                            FXMLDocumentController.this.id_output.appendText("识别用户名密码输入框失败\n");
                                        }
                                    });
                                    continue;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    continue;
                                }

                                if (FXMLDocumentController.this.id_havecaptcha.isSelected()) {
                                    if (FXMLDocumentController.this.captchadata == null) {
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                FXMLDocumentController.this.id_output.appendText("获取验证码失败\n");
                                            }
                                        });
                                        continue;
                                    }

                                    /*
                                    验证码识别
                                    * */

                                    if (yzm_yunRadioBtn.isSelected()) {
                                        String result = YunSu.createByPost(username1, password1, typeid1, to, softid1,
                                                softkey1, FXMLDocumentController.this.captchadata);
                                        System.out.println("查询结果:" + result);

//                                        int k = result.indexOf("|");
                                        if (result.contains("Error_Code")) {
                                            Platform.runLater(new Runnable() {
                                                public void run() {
                                                    FXMLDocumentController.this.id_output.appendText("获取验证码失败\n");
                                                }
                                            });
//                                            continue;
                                            break;
                                        }
                                        yzmText = YunSu.getResult(result);
                                    }
                                    if (yzm_localRadioBtn.isSelected()) {

                                        yzmText = YzmToText.getCode();

                                    }


                                    Platform.runLater(new Runnable() {
                                        public void run() {
                                            FXMLDocumentController.this.id_output.appendText("已识别验证码为:" + yzmText);
                                        }
                                    });
                                    try {
                                        if (FXMLDocumentController.this.id_captchabyid.isSelected()) {
                                            InputElement ele = (InputElement) doc.findElement(By.id(captchainput));
                                            ele.setValue(yzmText);
                                        } else if (FXMLDocumentController.this.id_captchabyname.isSelected()) {
                                            InputElement ele = (InputElement) doc.findElement(By.name(captchainput));
                                            ele.setValue(yzmText);
                                        } else {
                                            InputElement ele = (InputElement) doc
                                                    .findElement(By.className(captchainput));
                                            ele.setValue(yzmText);
                                        }
                                    } catch (IllegalStateException e) {
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                FXMLDocumentController.this.id_output.appendText("IllegalStateException异常,无法获取登录按钮,停止测试\n");
                                            }
                                        });
                                    }
                                }

                                try {
                                    if (FXMLDocumentController.this.id_submitbyid.isSelected())
                                        doc.findElement(By.id(submitinput)).click();
                                    else if (FXMLDocumentController.this.id_submitbyname.isSelected())
                                        doc.findElement(By.name(submitinput)).click();
                                    else
                                        doc.findElement(By.className(submitinput)).click();
                                } catch (Exception e) {
                                    try {
                                        doc.findElement(By.cssSelector("[type=submit]")).click();
                                    } catch (IllegalStateException ee) {
                                        break;
                                    }
                                }


                                browser.executeCommand(EditorCommand.INSERT_NEW_LINE);
                                if (interval.intValue() != 0) {
                                    Thread.sleep(interval.intValue());
                                }
                                Platform.runLater(new Runnable() {
                                    public void run() {
                                        FXMLDocumentController.this.id_output.appendText("点击登录后URL为:" + browser.getURL() + "\r\n\r\n");
                                    }
                                });
                            }
                            brpass.close();
                        }
                        bruser.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (this.id_tab2.isSelected()) {
            if ((this.id_havecaptcha2.isSelected()) && ((username1.equals("")) || (password1.equals("")))) {
                new Alert(Alert.AlertType.NONE, "云速账号密码不能为空", new ButtonType[]{ButtonType.CLOSE});
                return;
            }
            new Thread(new Runnable() {
                public void run() {
                    FXMLDocumentController.this.isstopsendcrack = Boolean.valueOf(false);

                    if (FXMLDocumentController.this.queue == null)
                        FXMLDocumentController.this.queue = new LinkedBlockingQueue(8100);
                    else {
                        FXMLDocumentController.this.queue.clear();
                    }

                    Integer thread = (Integer) FXMLDocumentController.this.id_threads2.getValue();
                    if (thread == null) {
                        thread = Integer.valueOf(1);
                    }
                    Integer timeout2 = (Integer) FXMLDocumentController.this.id_timeout2.getValue();
                    if (timeout2 == null) {
                        timeout2 = Integer.valueOf(3000);
                    }
                    CountDownLatch cdl = new CountDownLatch(thread.intValue());

                    String request = FXMLDocumentController.this.id_requestarea.getText();
                    if (request.equals("")) {
                        return;
                    }

                    String schema = loginurl.startsWith("http") ? "http" : "https";
                    String keyword2 = FXMLDocumentController.this.id_keyword2.getText();
                    String captchaurlinput2 = FXMLDocumentController.this.id_chatchaurlinput2.getText();

                    for (int i = 0; i < thread.intValue(); i++) {
                        new Thread(
                                new SendCrackThread(cdl, FXMLDocumentController.this.queue, request, schema, FXMLDocumentController.this.id_outputarea2, keyword2,
                                        captchaurlinput2, timeout2, Boolean.valueOf(FXMLDocumentController.this.id_havecaptcha2.isSelected()), yunSuConfig, loginurl))
                                .start();
                    }
                    try {
                        FileReader fruser = new FileReader("dict" + File.separator + "username.txt");
                        BufferedReader bruser = new BufferedReader(fruser);
                        String username;
                        while ((username = bruser.readLine()) != null) {
                            FileReader frpass = new FileReader("dict" + File.separator + "password.txt");
                            BufferedReader brpass = new BufferedReader(frpass);
                            String password;
                            while ((password = brpass.readLine()) != null) {
                                if (FXMLDocumentController.this.isstopsendcrack.booleanValue()) {
                                    FXMLDocumentController.this.queue.clear();
                                    bruser.close();
                                    brpass.close();
                                    cdl.await();
                                    return;
                                }
                                do
                                    Thread.sleep(300L);
                                while (FXMLDocumentController.this.queue.size() > 8000);

                                FXMLDocumentController.this.queue.add(new UserPassPair(username, password));
                            }

                            brpass.close();
                        }

                        bruser.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        while (cdl.getCount() > 0L) {
                            if (FXMLDocumentController.this.isstopsendcrack.booleanValue()) {
                                FXMLDocumentController.this.queue.clear();
                                break;
                            }
                            Thread.sleep(3000L);
                        }
                        cdl.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("爆破结束");
                    Platform.runLater(new Runnable() {
                        public void run() {
                            FXMLDocumentController.this.id_outputarea2.appendText("爆破结束。\n");
                        }
                    });
                }
            }).start();
        }
    }


    @FXML
    private void typehelp(ActionEvent event) {
        String url = "http://www.ysdm.net/home/PriceType";
        try {
            URI uri = URI.create(url);
            Desktop dp = Desktop.getDesktop();
            if (dp.isSupported(Desktop.Action.BROWSE))
                dp.browse(uri);
        } catch (NullPointerException localNullPointerException) {
        } catch (Exception e) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void help(ActionEvent event) {
        String url = "http://www.cnblogs.com/SEC-fsq/p/5712792.html";
        try {
            URI uri = URI.create(url);
            Desktop dp = Desktop.getDesktop();
            if (dp.isSupported(Desktop.Action.BROWSE))
                dp.browse(uri);
        } catch (NullPointerException localNullPointerException) {
        } catch (Exception e) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void showbrowser(ActionEvent event) {
        if (this.primaryStage == null) {
            return;
        }
        if (this.id_showbrowser.isSelected())
            this.primaryStage.show();
        else
            this.primaryStage.hide();
    }


    @FXML
    private void ydm(ActionEvent event) {


        this.id_hbox11.setDisable(false);
        this.id_hbox22.setDisable(false);
        this.id_hbox33.setDisable(false);
        this.id_hbox44.setDisable(false);
        this.id_hbox55.setDisable(false);
    }

    @FXML
    private void localdm(ActionEvent event) {


        this.id_hbox44.setDisable(true);
        this.id_hbox55.setDisable(true);
    }

    @FXML
    private void checkhavecaptcha(ActionEvent event) {
        if (this.id_havecaptcha.isSelected()) {
            this.id_hbox11.setDisable(false);
            this.id_hbox22.setDisable(false);
            this.id_hbox33.setDisable(false);
            this.id_hbox44.setDisable(false);
            this.id_hbox55.setDisable(false);
            this.yzm_localRadioBtn.setDisable(false);
            this.yzm_yunRadioBtn.setDisable(false);
        } else {
            this.id_hbox11.setDisable(true);
            this.id_hbox22.setDisable(true);
            this.id_hbox33.setDisable(true);
            this.id_hbox44.setDisable(true);
            this.id_hbox55.setDisable(true);
            this.yzm_localRadioBtn.setDisable(true);
            this.yzm_yunRadioBtn.setDisable(true);
        }
    }

    @FXML
    private void setusernamepos(ActionEvent event) {
        IndexRange selection = this.id_requestarea.getSelection();
        this.id_requestarea.replaceText(selection, "$username$");
    }

    @FXML
    private void setpasswordpos(ActionEvent event) {
        IndexRange selection = this.id_requestarea.getSelection();
        this.id_requestarea.replaceText(selection, "$password$");
    }

    @FXML
    private void setcaptchapos(ActionEvent event) {
        IndexRange selection = this.id_requestarea.getSelection();
        this.id_requestarea.replaceText(selection, "$captcha$");
    }

    @FXML
    private void stopsendcrack(ActionEvent event) {
        this.isstopsendcrack = Boolean.valueOf(true);
    }

    @FXML
    private void openyunsu(ActionEvent event) {
        String url = "http://www.ysdm.net/";
        try {
            URI uri = URI.create(url);
            Desktop dp = Desktop.getDesktop();
            if (dp.isSupported(Desktop.Action.BROWSE))
                dp.browse(uri);
        } catch (NullPointerException localNullPointerException) {
        } catch (Exception e) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public class MyNetworkDelegate extends DefaultNetworkDelegate {
        private boolean isCompleteAuth;
        private boolean isCancelAuth;
        private String captchaurl;
        private String url;
        private long currentid;
        private ByteArrayBuffer cap = new ByteArrayBuffer(4096);

        public MyNetworkDelegate(String captchaurl) {
            this.captchaurl = captchaurl;
            int i = captchaurl.indexOf("?");
            if (i != -1)
                this.url = captchaurl.substring(0, i);
            else
                this.url = captchaurl;
        }

        public void onBeforeSendHeaders(BeforeSendHeadersParams paramBeforeSendHeadersParams) {
            if (paramBeforeSendHeadersParams.getURL().startsWith(this.url)) {
                System.err.println("发起一次验证码请求。");
                paramBeforeSendHeadersParams.getHeadersEx().setHeader("Accept-Encoding", "");
                this.cap.clear();
            }
        }

        public void onDataReceived(DataReceivedParams paramDataReceivedParams) {
            String currenturl = paramDataReceivedParams.getURL();
            FileOutputStream fos = null;
            if (currenturl.startsWith(this.url)) {
                try {
                    System.out.println("已获取验证码数据:" + currenturl);
                    FXMLDocumentController.this.captchadata = paramDataReceivedParams.getData();
                    this.cap.append(FXMLDocumentController.this.captchadata, 0, FXMLDocumentController.this.captchadata.length);
                    FXMLDocumentController.this.captchadata = this.cap.toByteArray();

                    fos = new FileOutputStream(new File("tmp\\yzm.jpg"));
                    fos.write(FXMLDocumentController.this.captchadata);
//                    ImageIO.write(ImageIO.read(new File("tmp\\yzm.jpg")),"JPG",new File("tmp\\yzm2.jpg"));

                    fos.flush();
                    fos.close();


//                    System.out.println("验证码数据:" + new String(FXMLDocumentController.this.captchadata).substring(0, 100));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            String charset = paramDataReceivedParams.getCharset();
            if (charset.equals("")) {
                charset = "utf-8";
            }
            String x = null;
            try {
                x = new String(paramDataReceivedParams.getData(), charset);
//                System.out.println(x);
                if (x.contains(FXMLDocumentController.this.id_keyword.getText()))
                    Platform.runLater(new Runnable() {
                        public void run() {
                            FXMLDocumentController.this.id_output.appendText(paramDataReceivedParams.getURL() + ":关键字匹配成功。\n");
                        }
                    });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public boolean onAuthRequired(AuthRequiredParams paramAuthRequiredParams) {
            System.out.println("需要认证");
            this.isCompleteAuth = false;
            this.isCancelAuth = false;

            Platform.runLater(new Runnable() {
                public void run() {
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    TextField userfield = new TextField();
                    TextField passfield = new TextField();
                    userfield.setPromptText("用户名");
                    passfield.setPromptText("密码");

                    Button okbutton = new Button("确定");
                    Button cancelbutton = new Button("取消");

                    HBox hbox = new HBox(50.0D);
                    hbox.getChildren().addAll(new Node[]{okbutton, cancelbutton});

                    VBox vbox = new VBox(20.0D, new Node[]{userfield, passfield, hbox});
                    vbox.setPadding(new Insets(30.0D, 30.0D, 30.0D, 30.0D));

                    vbox.setAlignment(Pos.CENTER);
                    hbox.setAlignment(Pos.CENTER);

                    Scene scene = new Scene(vbox);
                    stage.setScene(scene);
                    stage.setTitle("请输入用户名密码");
                    stage.sizeToScene();
                    userfield.requestFocus();
                    EventHandler okaction = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            paramAuthRequiredParams.setUsername(userfield.getText());
                            paramAuthRequiredParams.setPassword(passfield.getText());
                            MyNetworkDelegate.this.isCancelAuth = false;
                            MyNetworkDelegate.this.isCompleteAuth = true;
                            stage.close();
                        }
                    };
                    EventHandler cancelaction = new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent arg0) {
                            MyNetworkDelegate.this.isCancelAuth = true;
                            MyNetworkDelegate.this.isCompleteAuth = true;
                            stage.close();
                        }
                    };


                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            MyNetworkDelegate.this.isCancelAuth = true;
                            System.out.println("hehe");
                            MyNetworkDelegate.this.isCompleteAuth = true;
                        }
                    });

                    okbutton.setOnAction(okaction);
                    userfield.setOnAction(okaction);
                    passfield.setOnAction(okaction);
                    cancelbutton.setOnAction(cancelaction);
                    stage.showAndWait();
                }
            });
            while (!this.isCompleteAuth) {
                System.out.println("等待输入账号密码");
            }
            System.out.println(this.isCancelAuth);
            return this.isCancelAuth;
        }
    }

}
