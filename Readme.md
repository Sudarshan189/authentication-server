Use key tool to generate the keystore with public and private key.

`keytool -genkey -alias <alias> -keyalg RSA -keystore <keystore_name>  -keysize 2048`

1. .alias — A name to uniquely identify the generated keypair entry within the generate keystore. Let’s use “jwtsigning”
2. keyalg — Public key algorithm. Should be “RSA” for our case.
3. keystore — A name for the keystore file generated. Let’s use “keystore.jks”
4. keysize — Size (a measure of strength) of the generated public key. We should set that to 2048 at least. Can be set to 4096 for better security (to further reduce the possibility of an attacker guessing your keys).

`keytool -genkey -alias jwtsigning -keyalg RSA -keystore keystore.jks  -keysize 2048`

Move jks file to `src/main/resources/keys/keystore.jks`