/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trashCode;

/**
 * đây là nơi chứa rác
 *
 * @author Admin
 */
public class trash {

//    public static void getMessage(Gmail service, String userId, String messageId) throws IOException, MessagingException {
//        Message message = service.users().messages().get(userId, messageId).setFormat("raw").execute();
//
//        Base64 base64Url = new Base64(true);
//        byte[] emailBytes = base64Url.decodeBase64(message.getRaw());
//
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
//        System.out.println("");
//        System.out.println("Mail ID: " + messageId);
//        System.out.println("Mail Snippet: " + message.getSnippet());
//        String from = email.getFrom()[0].toString();
//        System.out.println("From: " + Arrays.toString(email.getFrom()));
//        //System.out.println("Content: " + email.getContent());
//        Object content = email.getContent();
//        handlerMultiPart((Multipart) content);
//        System.out.println("");
//
//    }
    
    //trong hàm get message
    //        List<MessagePart> parts = payload.getParts();
//        parseBodyParts(service, userId, messageId, parts);
    //====================================================================
//        System.out.println(mimeType);
//        Base64 base64Url = new Base64(true);
//        byte[] emailBytes = base64Url.decodeBase64(message.getRaw());
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//        System.out.println(message.getRaw());
//        InputStream inputStream =new ByteArrayInputStream(message.getRaw().getBytes(Charset.forName("UTF-8")));
//        MimeMessage email = new MimeMessage(session, inputStream);
//        System.out.println("");
//        System.out.println("Mail ID: " + messageId);
//        System.out.println("Mail Snippet: " + message.getSnippet());
//        String from = email.getFrom()[0].toString();
//        System.out.println("From: " + Arrays.toString(email.getFrom()));
//        //System.out.println("Content: " + email.getContent());
//        Object content = email.getContent();
//        handlerMultiPart((Multipart) content);
//        System.out.println("");
    
        //không dùng
//    public static void handlerMultiPart(Multipart multipart) throws MessagingException, IOException {
//	for (int i = 0; i < multipart.getCount(); i++) {
//	    try {
//		System.out.println("Day la part: " + i);
////                handlerPart(multipart.getBodyPart(i));
//		writePart(multipart.getBodyPart(i));
//	    } catch (Exception ex) {
//		Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
//	    }
//	}
//    }

    //không dùng
//    public static void handlerPart(Part part) throws MessagingException, IOException {
//	String dposition = part.getDisposition();
//	String cType = part.getContentType();
//	String fname;
//	if (dposition == null) {
//	    System.out.println("Null" + cType);
//	    if ((cType.length() >= 10) && (cType.toLowerCase().substring(0, 10).equals("text/plain"))) {
//		System.out.println("++++++++++++++++++++++++++++++++++++TEXT/PLAIN+++++++++++++++++++++++++++++++++++++");
////                part.writeTo(System.out);
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//	    } else {
//		System.out.println("++++++++++++++++++++++++++++++++++++OTHER TYPE++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println("Other: " + cType);
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////                part.writeTo(System.out);
//	    }
//	} else if (dposition.equalsIgnoreCase(Part.ATTACHMENT)) {
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++ATTACHMENT+++++++++++++++++++++++++++++++++++++++++");
//	    System.out.println("Attachment" + part.getFileName() + " : " + cType);
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////            saveFile(part.getFileName(), part.getInputStream());
////            fname = part.getFileName();
////            System.out.println("######## DEBUG attachment ##### " + fname);
//	} else if (dposition.equalsIgnoreCase(Part.INLINE)) {
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++INLINE++++++++++++++++++++++++++++++++++++++++");
//	    System.out.println("Inline" + part.getFileName() + " : " + cType);
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////            saveFile(part.getFileName(), part.getInputStream());
////            fname = part.getFileName()
////            println("######## DEBUG inline ##### " + fname)
//	} else {
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++OTHER DPOSITION++++++++++++++++++++++++++++++++++++++++++");
//	    System.out.println("Other dposition");
//	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//	}
//    }
//
//    // không dùng
//    public static void saveFile(String filename, InputStream input) throws IOException {
//	if (filename == null) {
//	    filename = File.createTempFile("MailAttacheFile", ".out").getName();
//	}
//	System.out.println("downloadingAttachment..." + filename);
//	File file = new File(filename);
//	for (int i = 0; file.exists(); i++) {
//	    file = new File(filename + i);
//	}
//	FileOutputStream fos = new FileOutputStream(file);
//	BufferedOutputStream bos = new BufferedOutputStream(fos);
//	BufferedInputStream bis = new BufferedInputStream(input);
//	int fByte;
//	while ((fByte = bis.read()) != -1) {
//	    bos.write(fByte);
//	}
//	bos.flush();
//	bos.close();
//	bis.close();
//	System.out.println("done...");
//    }
//    static int i = 0;
//
//    // không dùng
//    public static void writePart(Part p) throws Exception {
//	i++;
//	System.out.println("---------------------------- " + i);
//	System.out.println("CONTENT-TYPE: " + p.getContentType());
//
//	//check if the content is plain text
//	if (p.isMimeType("text/plain")) {
//	    System.out.println("This is plain text");
//	    System.out.println("------------------------------------------------------------------------------------");
//	    String test = p.getContent().toString();
////            try {
////                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
////            } catch (UnsupportedEncodingException e) {
////                throw new InternalError("VM does not support mandatory encoding UTF-8");
////            }
////            byte[] myBytes = test.getBytes(Charset.forName("UTF-8"));
////            String originalString = new String(myBytes,"UTF-8");
////            System.out.println(originalString);
//
//	    System.out.println(test);
////            String parse = new String(test.getBytes(), "iso-8859-1");
////            System.out.println(parse);
////            String newString = new String(parse.getBytes(), "UTF8");
////            System.out.println(newString);
//
////            System.out.println((String) p.getContent());
//	} //check if the content has attachment
//	else if (p.isMimeType("multipart/*")) {
//	    System.out.println("This is a Multipart");
//	    System.out.println("------------------------------------------------------------------------------------");
//	    Multipart mp = (Multipart) p.getContent();
//	    int count = mp.getCount();
//	    for (int i = 0; i < count; i++) {
//		writePart(mp.getBodyPart(i));
//	    }
//	} //check if the content is a nested message
//	else if (p.isMimeType("message/rfc822")) {
//	    System.out.println("This is a Nested Message");
//	    System.out.println("------------------------------------------------------------------------------------");
//	    writePart((Part) p.getContent());
//	} //check if the content is an inline image
//	else if (p.isMimeType("image/jpeg")) {
//	    System.out.println("--------> image/jpeg------------------------------------------------------------------------------------");
//	    System.out.println("Ban co 1 ảnh ");
////            Object o = p.getContent();
////
////            InputStream x = (InputStream) o;
////            // Construct the required byte array
////            int i = 0;
////            byte[] bArray = new byte[x.available()];
////            System.out.println("x.length = " + x.available());
////            while ((i = (int) ((InputStream) x).available()) > 0) {
////                int result = (int) (((InputStream) x).read(bArray));
////                if (result == -1) {
////                    break;
////                }
////            }
////            FileOutputStream f2 = new FileOutputStream("image.jpg");
////            f2.write(bArray);
//	} else if (p.getContentType().contains("image/")) {
//	    System.out.println("Ban co 1 anh ");
//	    System.out.println("Content type: " + p.getContentType());
////            File f = new File("image" + new Date().getTime() + ".jpg");
////            DataOutputStream output = new DataOutputStream(
////                    new BufferedOutputStream(new FileOutputStream(f)));
////            com.sun.mail.util.BASE64DecoderStream test
////                    = (com.sun.mail.util.BASE64DecoderStream) p
////                            .getContent();
////            byte[] buffer = new byte[1024];
////            int bytesRead;
////            while ((bytesRead = test.read(buffer)) != -1) {
////                output.write(buffer, 0, bytesRead);
////            }
//	} else {
//	    Object o = p.getContent();
//	    if (o instanceof String) {
//		System.out.println("This is a string");
//		System.out.println("------------------------------------------------------------------------------------");
////                System.out.println((String) o);
//	    } else if (o instanceof InputStream) {
//		System.out.println("This is just an input stream");
//		System.out.println("------------------------------------------------------------------------------------");
////                InputStream is = (InputStream) o;
////                is = (InputStream) o;
////                int c;
////                while ((c = is.read()) != -1) {
////                    System.out.write(c);
////                }
//	    } else {
//		System.out.println("This is an unknown type");
//		System.out.println("------------------------------------------------------------------------------------");
//		System.out.println(o.toString());
//	    }
//	}
//    }
}
