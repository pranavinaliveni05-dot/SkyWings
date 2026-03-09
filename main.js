// Tab switching
function showTab(tabName) {
    // Update tab buttons
    const tabs = document.querySelectorAll('.tab-btn');
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });
    event.target.classList.add('active');
    
    // Update tab content
    const contents = document.querySelectorAll('.tab-content');
    contents.forEach(content => {
        content.classList.remove('active');
    });
    document.getElementById(tabName + 'Tab').classList.add('active');
}

// Flight status search
function searchFlightStatus() {
    const searchInput = document.getElementById('flightSearch');
    if (searchInput && searchInput.value.trim() !== '') {
        alert('Searching for flight: ' + searchInput.value + ' (Demo feature)');
    } else {
        alert('Please enter a flight number or route');
    }
}

// Cancel booking
function cancelBooking() {
    const message = document.getElementById('cancellationMessage');
    if (message) {
        message.style.display = 'block';
        setTimeout(() => {
            window.location.href = 'my-bookings.html';
        }, 3000);
    }
}

// Form validation for registration
function validateRegistration() {
    const password = document.querySelector('input[type="password"]');
    const confirmPassword = document.querySelectorAll('input[type="password"]')[1];
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
        alert('Passwords do not match!');
        return false;
    }
    return true;
}