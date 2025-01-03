package com.orial.cripto.service.impl;

import com.orial.cripto.entity.Price;
import com.orial.cripto.repository.PriceRepository;
import com.orial.cripto.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.orial.cripto.util.CriptoUtils.*;
import static java.lang.String.format;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService, Serializable {

    private final File file = new File(FILE_PATH);
    private final PriceRepository priceRepository;
    private final Comparator<Price> sortingByLowerPrice = Comparator.comparing(Price::getLowerPrice);

    @Override
    public List<Price> findAllByCryptocurrency(String cryptocurrency) {
        List<Price> prices = priceRepository.findAllByCryptocurrency(cryptocurrency);

        if (isEmpty(prices)) {
            throw new RuntimeException("empty");
        }
        return prices;
    }

    @Override
    public Price minPriceByCryptocurrency(String cryptocurrency) {
        return priceRepository.findAllByCryptocurrency(cryptocurrency).stream()
                .min(sortingByLowerPrice)
                .orElseThrow(() -> new RuntimeException(format(cryptocurrency, NOT_FOUND)));
    }

    @Override
    public Price maxPriceByCryptocurrency(String cryptocurrency) {
        List<Price> prices = priceRepository.findAllByCryptocurrency(cryptocurrency);

        return prices.stream()
                .max(sortingByLowerPrice)
                .orElseThrow(() -> new RuntimeException(format(cryptocurrency, NOT_FOUND)));
    }


    private List<String> informationForCsv(String cryptocurrency) {
        Price min = this.minPriceByCryptocurrency(cryptocurrency);
        Price max = maxPriceByCryptocurrency(cryptocurrency);
        String name = String.format("\nName is %s\n", cryptocurrency);
        String highest = String.format("The highest price is %s;\n", max.getLowerPrice());
        String lower = String.format("The lowest price is %s;\n\n", min.getLowerPrice());
        return Arrays.asList(name, highest, lower);
    }

    @Override
    public void writeCsv() throws IOException {
        List<String> btcInformation = informationForCsv(BTC);
        List<String> ethInformation = informationForCsv(ETH);
        List<String> xrpInformation = informationForCsv(XRP);

        try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            outputStream.write(LocalDateTime.now().toString().getBytes());
            outputStream.write((btcInformation.get(0) + btcInformation.get(1) + btcInformation.get(2)).getBytes());
            outputStream.write((ethInformation.get(0) + ethInformation.get(1) + ethInformation.get(2)).getBytes());
            outputStream.write((xrpInformation.get(0) + xrpInformation.get(1) + xrpInformation.get(2)).getBytes());

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}

