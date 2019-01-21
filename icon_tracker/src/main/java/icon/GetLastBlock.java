package icon;

import java.io.IOException;
import java.io.IOException;

import foundation.icon.icx.IconService;
import foundation.icon.icx.data.Block;
import foundation.icon.icx.transport.http.HttpProvider;
import okhttp3.OkHttpClient;

public class GetLastBlock {
	public final String URL = "https://wallet.icon.foundation/api/v3";
	private IconService iconService;

	public void GetLastBlock() {
		OkHttpClient httpClient = new OkHttpClient.Builder().build();
		iconService = new IconService(new HttpProvider(httpClient, URL));
	}

	public Block getLastBlock() throws IOException {
		Block block = iconService.getLastBlock().execute();
		System.out.println("block:" + block);
		return block;
	}
}
