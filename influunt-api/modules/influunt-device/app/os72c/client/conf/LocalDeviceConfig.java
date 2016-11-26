package os72c.client.conf;

/**
 * Created by rodrigosol on 11/15/16.
 */
public class LocalDeviceConfig implements DeviceConfig {

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getPort() {
        return "1883";
    }

    @Override
    public String getDeviceId() {
        return "7abfa23d-5646-4b8c-87ae-e68addbabb36";
    }

    @Override
    public void setDeviceId(String id) {

    }

    @Override
    public String getCentralPublicKey() {
        return "30819f300d06092a864886f70d010101050003818d0030818902818100d39d5d72a816c3bb5b4de6d775f8de49e49c21de189a1e6c63b57c26f8a9fd642a8b103d8b1dc7b27c860db0a3291d31083ff64b4334ae9a227dfaceca857d09c210e0cf8e97f62bf74d4fcf1f3742a869b8b9932b12bb37070e2fa6fffae489441243ee115efb27366526ff08ead6e80f22508d8c548225e206f55e6b01a3130203010001";
    }

    @Override
    public void setCentralPublicKey(String publicKey) {

    }

    @Override
    public String getPrivateKey() {
        return "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810090ee31729b24c6b4723ce53c5bcb53b6ef1153bc3c2d9a81cd6ea226afc4cb283fbb563b263823e81e866e5e9108b434a976b5dbe75b2d0b976c7c96c8373db91fa4089e04c562c633d6b3cadb71e221a9239b7e8b0edcdb8a6517abc5df537a26a78d8b932124612d5cd1ea2781a19a660d8b42e015c2d05dbd64720bc1eedb0203010001028180473dbef2be0e82da7a793299cbe993ba0da42fcc0cacc21162412ad5e77acdfe77ed8782c5d3bcdac1fe84cc5970b4dc5a1cef622c44a64cd75f4d92f4d22ab552abfbcfbc0f6adedc4b5b67dad4c66d5b797c4e727a8f7ef5ed91a94d7dcd22c96d0a78330a32eff0d7c384fbe828b01d00fbcfe7fde034828993b62e0ec411024100c91961a43a9fa50ec7f87f34e944a14b181f694c4bd93aee9d866791df6c5e2732751aafbca301d24f6270730175deed3a0a3a719a2ed205b092668c8abc1af9024100b87f3cb12caf257d31fbb0912906f6e6dfaa4c3eec67e10ea1a4a012e9f5ebdeefd7fc3ce0b7827b0098880023066c6e03c5431834bf42f8f39b21e173fb997302402c19a8d3ff172b77465b346a420d8ee18cf4b67ec8d3669d15667828013b1e9ab1f2045c2d9e01b4413c71348749760dc8009e38737b790b4e618ca39d790ae1024036aae4f5eeb29b1e24a05a6a38c8ea81f0864a7f5e974d3583a9ff107ba8b5d2622912818b6874ce3397febc63a4749e88997bcf015a26e31373c52f43a9aa87024074c730531a314ddef81119ed194356df5679589567fa635f669b4f4974aee25d0cbb0c078486fe62156efcde30091d02b89a643c86ca1e4ca873b349cfec2411";
    }

    @Override
    public void setPrivateKey(String publicKey) {

    }
}
