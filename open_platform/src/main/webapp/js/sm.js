function doGenerate() {
  var f1 = document.form1;
  var curve = f1.curve1.value;
  var ec = new KJUR.crypto.ECDSA({"curve": curve});
  var keypair = ec.generateKeyPairHex();

  f1.prvkey1.value = keypair.ecprvhex;
  f1.pubkey1.value = keypair.ecpubhex;
}
function SM3Encrypt(msg){
	var cipher = new SM2Cipher(0);
	var c3= new Array(32);
	var sm3c3 = new SM3Digest();
	var test1 = CryptoJS.enc.Utf8.parse(msg);
	var data = cipher.GetWords(test1.toString());
    sm3c3.BlockUpdate(data, 0, data.length);
    sm3c3.DoFinal(c3, 0);
	var wordArray = cipher.GetHex(c3); 
	var s = new String(wordArray);
	return s;
}

function doCrypt(msg,pubkeyHex) {
    var msgData = CryptoJS.enc.Utf8.parse(msg);
    if (pubkeyHex.length > 64 * 2) {
        pubkeyHex = pubkeyHex.substr(pubkeyHex.length - 64 * 2);
    }
    var xHex = pubkeyHex.substr(0, 64);
    var yHex = pubkeyHex.substr(64);
	
	//var cipherMode = f1.cipherMode.value;
    //zjc 0代表C1C2C3加密 1代表C1C3C2方式
    var cipher = new SM2Cipher(0);
    var userKey = cipher.CreatePoint(xHex, yHex);
	
	msgData = cipher.GetWords(msgData.toString());
	//加密后数据
    var encryptData = cipher.Encrypt(userKey, msgData);
    return encryptData;
}
function sm3DoCrypt(msg,pubkeyHex) {
    var msgData = CryptoJS.enc.Utf8.parse(msg);
    if (pubkeyHex.length > 64 * 2) {
        pubkeyHex = pubkeyHex.substr(pubkeyHex.length - 64 * 2);
    }
    var xHex = pubkeyHex.substr(0, 64);
    var yHex = pubkeyHex.substr(64);
	
	//var cipherMode = f1.cipherMode.value;
    //zjc 0代表C1C2C3加密 1代表C1C3C2方式
    var cipher = new SM2Cipher(0);
    var userKey = cipher.CreatePoint(xHex, yHex);
	
	msgData = cipher.GetWords(msgData.toString());
	//加密后数据
    var encryptData = cipher.Encrypt(userKey, msgData);
    var str="";
    return str;
}
function doDecrypt(encryptData,prvkey) {
    var privateKey = new BigInteger(prvkey, 16);
    var cipherMode = "0";//C1C2C3
    var cipher = new SM2Cipher(cipherMode);
    var data = cipher.Decrypt(privateKey, encryptData);
    return data;
}
function doDecrypt1() {
    var prvkey = "00b1fc2f45f13ef4b1f96216f46808d2e13aef36b2a1a65409ea94f5ace4aad0";
    var encryptData = "a8ef2b3015007981b0b2581dcc4518d31967341b24664d0ebf7052ed8038ae72fe3b5ed445717d8965341a347fe6d099610e0dc92d013b36d3e13acceaa2fd749f85dd5385bd2617818ea9d041f1f52654aa6dfcd1d513fc516abc9a815a468936f88185cc";
    var privateKey = new BigInteger(prvkey, 16);
	
    var cipherMode = 0;
    var cipher = new SM2Cipher(cipherMode);
    var data = cipher.Decrypt(privateKey, encryptData);
	
    alert(data ? '解密成功，原文：' + data : '解密失败！');
}

function certCrypt() {
  	var certData = document.getElementById('txtCertData').value;
  	if( certData != "") {
  		var key = X509.getPublicKeyFromCertPEM(certData);
		document.getElementById('txtPubKey').value = key.pubKeyHex;
  	}
  	var pubkey = document.getElementById('txtPubKey').value.replace(/\s/g,'');
    var pubkeyHex = pubkey;
    if (pubkeyHex.length > 64 * 2) {
        pubkeyHex = pubkeyHex.substr(pubkeyHex.length - 64 * 2);
    }
    var xHex = pubkeyHex.substr(0, 64);
    var yHex = pubkeyHex.substr(64);
	
	var cipherMode = document.getElementById('cipherMode').value;
    
    var cipher = new SM2Cipher(cipherMode);
    var userKey = cipher.CreatePoint(xHex, yHex);
	
	var msg = document.getElementById('txtRawData').value;
	var msgData = CryptoJS.enc.Utf8.parse(msg);
	msgData = cipher.GetWords(msgData.toString());
	
    var encryptData = cipher.Encrypt(userKey, msgData);
    document.getElementById('txtCryptData').value = hex2b64(encryptData);
}