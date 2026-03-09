// Seat selection
let selectedSeats = [];

function selectSeat(seat) {
    if (seat.classList.contains('occupied')) {
        alert('This seat is already occupied!');
        return;
    }
    
    seat.classList.toggle('selected');
    
    const seatNumber = seat.textContent;
    if (seat.classList.contains('selected')) {
        if (!selectedSeats.includes(seatNumber)) {
            selectedSeats.push(seatNumber);
        }
    } else {
        selectedSeats = selectedSeats.filter(s => s !== seatNumber);
    }
    
    updateSelectedSeats();
}

function updateSelectedSeats() {
    const display = document.getElementById('selectedSeats');
    if (!display) return;
    
    if (selectedSeats.length > 0) {
        display.textContent = selectedSeats.join(', ');
    } else {
        display.textContent = 'None';
    }
    
    // Update price (example: $100 per seat)
    const totalPrice = selectedSeats.length * 100;
    const seatPrice = document.getElementById('seatPrice');
    if (seatPrice) {
        seatPrice.textContent = '$' + totalPrice;
    }
}

function confirmSeatSelection() {
    if (selectedSeats.length === 0) {
        alert('Please select at least one seat!');
    } else {
        alert('Seats ' + selectedSeats.join(', ') + ' confirmed! Proceeding to payment...');
        window.location.href = 'my-bookings.html';
    }
}

// Make selectedSeats available globally
window.selectedSeats = selectedSeats;

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    updateSelectedSeats();
});