// Currency conversion rates (simplified)
const exchangeRates = {
    USD: 1,
    EUR: 0.92,
    GBP: 0.79,
    JPY: 150.5,
    INR: 83.12,
    AUD: 1.52,
    CAD: 1.35,
    CHF: 0.88,
    CNY: 7.19,
    SGD: 1.34
};

const currencySymbols = {
    USD: '$',
    EUR: '€',
    GBP: '£',
    JPY: '¥',
    INR: '₹',
    AUD: 'A$',
    CAD: 'C$',
    CHF: 'Fr',
    CNY: '¥',
    SGD: 'S$'
};

// Currency conversion
function updateCurrency() {
    const select = document.getElementById('currencySelect');
    if (!select) return;
    
    const targetCurrency = select.value;
    const symbol = currencySymbols[targetCurrency];
    
    // Update all currency values
    document.querySelectorAll('.currency-value').forEach(element => {
        const usdValue = parseFloat(element.getAttribute('data-usd'));
        if (!isNaN(usdValue)) {
            const convertedValue = usdValue * exchangeRates[targetCurrency];
            element.textContent = symbol + convertedValue.toFixed(2);
        }
    });
    
    // Update seat price if on seat booking page
    const seatPriceElement = document.getElementById('seatPrice');
    if (seatPriceElement && window.selectedSeats && window.selectedSeats.length > 0) {
        const totalUSD = window.selectedSeats.length * 100;
        const convertedTotal = totalUSD * exchangeRates[targetCurrency];
        seatPriceElement.textContent = symbol + convertedTotal.toFixed(2);
    }
}

// Initialize currency on page load
document.addEventListener('DOMContentLoaded', function() {
    // Set initial currency
    const currencySelect = document.getElementById('currencySelect');
    if (currencySelect) {
        currencySelect.value = 'USD';
    }
});