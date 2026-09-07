/**
 * TrackMint Frontend Dashboard Application Logic
 * Inspired by Finexy design system
 */

(function () {
  'use strict';

  // --- Initial State & Storage ---
  const STORAGE_KEY = 'trackmint_dashboard_state';

  const defaultState = {
    theme: 'light',
    userName: 'Sajibur Rahman',
    currency: {
      code: 'USD',
      symbol: '$',
      rate: 1,
      flag: '🇺🇸'
    },
    totalBalance: 689372.00,
    totalEarnings: 950.00,
    totalSpending: 700.00,
    totalIncome: 1050.00,
    totalRevenue: 850.00,
    spendingLimit: 5500.00,
    spentAmount: 1400.00,
    chartData: [
      { month: 'Jan', profit: 24, loss: 38 },
      { month: 'Feb', profit: 32, loss: 22 },
      { month: 'Mar', profit: 26, loss: 40 },
      { month: 'Apr', profit: 30, loss: 42 },
      { month: 'May', profit: 34, loss: 44 },
      { month: 'Jun', profit: 46, loss: 48 },
      { month: 'Jul', profit: 28, loss: 38 },
      { month: 'Aug', profit: 30, loss: 32 }
    ],
    activities: [
      {
        id: 'INV_000076',
        title: 'Mobile App Purchase',
        category: 'SOFTWARE',
        icon: 'apple',
        price: 25500.00,
        status: 'Completed',
        date: '17 Apr, 2026 03:45 PM',
        selected: false
      },
      {
        id: 'INV_000075',
        title: 'Hotel Booking',
        category: 'HOTEL',
        icon: 'hotel',
        price: 32750.00,
        status: 'Pending',
        date: '15 Apr, 2026 11:30 AM',
        selected: false
      },
      {
        id: 'INV_000074',
        title: 'Flight Ticket Booking',
        category: 'TRAVEL',
        icon: 'flight',
        price: 40200.00,
        status: 'Completed',
        date: '15 Apr, 2026 12:00 PM',
        selected: false
      },
      {
        id: 'INV_000073',
        title: 'Grocery Purchase',
        category: 'GROCERY',
        icon: 'grocery',
        price: 50200.00,
        status: 'In Progress',
        date: '14 Apr, 2026 09:15 PM',
        selected: true
      },
      {
        id: 'INV_000072',
        title: 'Software License',
        category: 'SOFTWARE',
        icon: 'adobe',
        price: 15900.00,
        status: 'Completed',
        date: '10 Apr, 2026 06:00 AM',
        selected: false
      }
    ]
  };

  let state = loadState();

  function loadState() {
    try {
      const saved = localStorage.getItem(STORAGE_KEY);
      if (saved) return { ...defaultState, ...JSON.parse(saved) };
    } catch (e) {
      console.warn('Could not load stored state:', e);
    }
    return defaultState;
  }

  function saveState() {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
    } catch (e) {
      console.warn('Could not save state:', e);
    }
  }

  // --- Formatting Helpers ---
  function formatMoney(baseAmount, includeSymbol = true) {
    const converted = baseAmount * state.currency.rate;
    const formatted = converted.toLocaleString('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
    return includeSymbol ? `${state.currency.symbol}${formatted}` : formatted;
  }

  function formatShortMoney(baseAmount) {
    const converted = baseAmount * state.currency.rate;
    if (converted >= 1000000) {
      return `${state.currency.symbol}${(converted / 1000000).toFixed(1)}M`;
    }
    if (converted >= 1000) {
      return `${state.currency.symbol}${Math.round(converted / 1000)}k`;
    }
    return `${state.currency.symbol}${Math.round(converted)}`;
  }

  // --- UI Elements ---
  const docHtml = document.documentElement;
  const themeToggleBtn = document.getElementById('themeToggleBtn');
  const lightModeBtn = document.getElementById('lightModeBtn');
  const darkModeBtn = document.getElementById('darkModeBtn');
  
  const totalBalanceVal = document.getElementById('totalBalanceVal');
  const totalEarningsVal = document.getElementById('totalEarningsVal');
  const totalSpendingVal = document.getElementById('totalSpendingVal');
  const totalIncomeVal = document.getElementById('totalIncomeVal');
  const totalRevenueVal = document.getElementById('totalRevenueVal');
  
  const spentValText = document.getElementById('spentValText');
  const totalLimitValText = document.getElementById('totalLimitValText');
  const limitPctBadge = document.getElementById('limitPctBadge');
  const spendingProgressFill = document.getElementById('spendingProgressFill');
  
  const currencySelectBtn = document.getElementById('currencySelectBtn');
  const currencyDropdownMenu = document.getElementById('currencyDropdownMenu');
  const currentCurrencyCode = document.getElementById('currentCurrencyCode');
  const activeFlagIcon = document.getElementById('activeFlagIcon');
  
  const activitySearchInput = document.getElementById('activitySearchInput');
  const filterToggleBtn = document.getElementById('filterToggleBtn');
  const filterDropdownMenu = document.getElementById('filterDropdownMenu');
  const activitiesTableBody = document.getElementById('activitiesTableBody');
  const selectAllCheckbox = document.getElementById('selectAllCheckbox');
  const selectedCountMsg = document.getElementById('selectedCountMsg');
  const exportCsvBtn = document.getElementById('exportCsvBtn');

  // Modals
  const addExpenseModal = document.getElementById('addExpenseModal');
  const openAddExpenseBtn = document.getElementById('openAddExpenseBtn');
  const closeAddExpenseModal = document.getElementById('closeAddExpenseModal');
  const cancelAddExpenseBtn = document.getElementById('cancelAddExpenseBtn');
  const addExpenseForm = document.getElementById('addExpenseForm');
  const expenseDateInput = document.getElementById('expenseDateInput');

  const setBudgetModal = document.getElementById('setBudgetModal');
  const openSetBudgetBtn = document.getElementById('openSetBudgetBtn');
  const closeSetBudgetModal = document.getElementById('closeSetBudgetModal');
  const cancelSetBudgetBtn = document.getElementById('cancelSetBudgetBtn');
  const setBudgetForm = document.getElementById('setBudgetForm');
  const budgetLimitInput = document.getElementById('budgetLimitInput');

  const helpModal = document.getElementById('helpModal');
  const helpBtn = document.getElementById('helpBtn');
  const closeHelpModal = document.getElementById('closeHelpModal');
  const okHelpBtn = document.getElementById('okHelpBtn');

  const toastContainer = document.getElementById('toastContainer');

  let currentStatusFilter = 'ALL';
  let currentSearchQuery = '';

  // --- Initial Render ---
  function init() {
    applyTheme(state.theme);
    renderAllMetrics();
    renderChart();
    renderActivitiesTable();
    setupEventListeners();
  }

  // --- Theme Controller ---
  function applyTheme(theme) {
    state.theme = theme;
    docHtml.setAttribute('data-theme', theme);
    if (theme === 'dark') {
      lightModeBtn.classList.remove('active');
      darkModeBtn.classList.add('active');
    } else {
      darkModeBtn.classList.remove('active');
      lightModeBtn.classList.add('active');
    }
    saveState();
    renderChart(); // re-render chart axis colors if needed
  }

  // --- Metrics Renderer ---
  function renderAllMetrics() {
    // Total balance
    totalBalanceVal.textContent = formatMoney(state.totalBalance);
    totalEarningsVal.textContent = formatMoney(state.totalEarnings);
    totalSpendingVal.textContent = formatMoney(state.totalSpending);
    totalIncomeVal.textContent = formatMoney(state.totalIncome);
    totalRevenueVal.textContent = formatMoney(state.totalRevenue);

    // Spending Limit Progress
    const pct = Math.min(100, (state.spentAmount / state.spendingLimit) * 100);
    limitPctBadge.textContent = `${pct.toFixed(1)}%`;
    spendingProgressFill.style.width = `${pct}%`;
    spentValText.innerHTML = `<strong>${formatMoney(state.spentAmount)}</strong> spent out of`;
    totalLimitValText.textContent = formatMoney(state.spendingLimit);

    // Currency selector UI
    currentCurrencyCode.textContent = state.currency.code;
    activeFlagIcon.textContent = state.currency.flag;
  }

  // --- Dynamic SVG Bar Chart ---
  function renderChart() {
    const barsGroup = document.getElementById('chartBarsGroup');
    if (!barsGroup) return;
    barsGroup.innerHTML = '';

    const startX = 65;
    const spacing = 50;
    const barWidth = 14;
    const baselineY = 210;
    const maxVal = 50;
    const chartHeight = 180; // 210 - 30

    state.chartData.forEach((item, index) => {
      const groupX = startX + (index * spacing);
      
      // Calculate bar heights
      const profitHeight = (item.profit / maxVal) * chartHeight;
      const lossHeight = (item.loss / maxVal) * chartHeight;

      const profitY = baselineY - profitHeight;
      const lossY = baselineY - lossHeight;

      // Group for the pair
      const g = document.createElementNS('http://www.w3.org/2000/svg', 'g');

      // Month text label
      const text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
      text.setAttribute('x', groupX + barWidth);
      text.setAttribute('y', 230);
      text.setAttribute('text-anchor', 'middle');
      text.setAttribute('class', 'axis-label');
      text.textContent = item.month;
      g.appendChild(text);

      // Loss Bar (Orange Striped Pattern)
      const lossBar = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
      lossBar.setAttribute('x', groupX);
      lossBar.setAttribute('y', lossY);
      lossBar.setAttribute('width', barWidth);
      lossBar.setAttribute('height', lossHeight);
      lossBar.setAttribute('rx', 4);
      lossBar.setAttribute('fill', 'url(#stripedLossPattern)');
      lossBar.setAttribute('class', 'chart-bar-rect');
      attachTooltip(lossBar, `${item.month} Loss/Spent: $${item.loss}k`);
      g.appendChild(lossBar);

      // Profit Bar (Charcoal / Blue)
      const profitBar = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
      profitBar.setAttribute('x', groupX + barWidth + 2);
      profitBar.setAttribute('y', profitY);
      profitBar.setAttribute('width', barWidth);
      profitBar.setAttribute('height', profitHeight);
      profitBar.setAttribute('rx', 4);
      profitBar.setAttribute('fill', state.theme === 'dark' ? '#38BDF8' : '#191C21');
      profitBar.setAttribute('class', 'chart-bar-rect');
      attachTooltip(profitBar, `${item.month} Profit: $${item.profit}k`);
      g.appendChild(profitBar);

      barsGroup.appendChild(g);
    });
  }

  // --- Tooltip Handler ---
  const chartTooltip = document.getElementById('chartTooltip');
  function attachTooltip(element, text) {
    element.addEventListener('mouseenter', (e) => {
      chartTooltip.textContent = text;
      chartTooltip.style.opacity = '1';
      moveTooltip(e);
    });
    element.addEventListener('mousemove', moveTooltip);
    element.addEventListener('mouseleave', () => {
      chartTooltip.style.opacity = '0';
    });
  }

  function moveTooltip(e) {
    const wrapper = document.getElementById('incomeChartWrapper');
    const rect = wrapper.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top - 12;
    chartTooltip.style.left = `${x}px`;
    chartTooltip.style.top = `${y}px`;
  }

  // --- Activities Table Renderer ---
  function renderActivitiesTable() {
    activitiesTableBody.innerHTML = '';

    const filtered = state.activities.filter(act => {
      const matchesStatus = currentStatusFilter === 'ALL' || act.status === currentStatusFilter;
      const matchesSearch = !currentSearchQuery || 
        act.title.toLowerCase().includes(currentSearchQuery) || 
        act.id.toLowerCase().includes(currentSearchQuery) || 
        act.category.toLowerCase().includes(currentSearchQuery);
      return matchesStatus && matchesSearch;
    });

    if (filtered.length === 0) {
      activitiesTableBody.innerHTML = `
        <tr>
          <td colspan="7" style="text-align: center; padding: 36px; color: var(--text-light);">
            No activities found matching your criteria.
          </td>
        </tr>
      `;
      updateSelectedCount();
      return;
    }

    filtered.forEach(act => {
      const tr = document.createElement('tr');
      
      const iconType = getIconType(act);
      const statusClass = act.status.toLowerCase().replace(/\s+/g, '-');

      tr.innerHTML = `
        <td class="col-chk">
          <input type="checkbox" class="row-checkbox" data-id="${act.id}" ${act.selected ? 'checked' : ''} />
        </td>
        <td class="order-id-code">${act.id}</td>
        <td>
          <div class="activity-cell">
            <div class="activity-icon ${iconType.class}">${iconType.symbol}</div>
            <span class="activity-name">${act.title}</span>
          </div>
        </td>
        <td class="price-bold">${formatMoney(act.price)}</td>
        <td>
          <span class="status-dot-badge ${statusClass}">
            <span class="dot"></span>
            ${act.status}
          </span>
        </td>
        <td class="date-text">${act.date}</td>
        <td class="col-action">
          <button class="row-action-btn" data-delete-id="${act.id}" title="Delete Activity">✕</button>
        </td>
      `;

      activitiesTableBody.appendChild(tr);
    });

    updateSelectedCount();
  }

  function getIconType(act) {
    if (act.icon === 'apple' || act.title.includes('App')) return { class: 'apple', symbol: '' };
    if (act.icon === 'hotel' || act.category === 'HOTEL') return { class: 'hotel', symbol: '🏨' };
    if (act.icon === 'flight' || act.category === 'TRAVEL') return { class: 'flight', symbol: '✈' };
    if (act.icon === 'grocery' || act.category === 'GROCERY') return { class: 'grocery', symbol: '❇' };
    if (act.icon === 'adobe' || act.title.includes('Software')) return { class: 'adobe', symbol: 'A' };
    return { class: 'default', symbol: '💳' };
  }

  function updateSelectedCount() {
    const selectedCount = state.activities.filter(a => a.selected).length;
    selectedCountMsg.textContent = `${selectedCount} items selected`;
    selectAllCheckbox.checked = state.activities.length > 0 && selectedCount === state.activities.length;
  }

  // --- Event Listeners ---
  function setupEventListeners() {
    // Theme toggle
    themeToggleBtn.addEventListener('click', () => {
      applyTheme(state.theme === 'light' ? 'dark' : 'light');
      showToast(`Switched to ${state.theme} mode`);
    });

    // Currency selector
    currencySelectBtn.addEventListener('click', (e) => {
      e.stopPropagation();
      currencyDropdownMenu.classList.toggle('show');
    });

    document.querySelectorAll('.currency-option').forEach(opt => {
      opt.addEventListener('click', () => {
        const code = opt.getAttribute('data-curr');
        const symbol = opt.getAttribute('data-symbol');
        const rate = parseFloat(opt.getAttribute('data-rate'));
        const flag = opt.getAttribute('data-flag');

        state.currency = { code, symbol, rate, flag };
        document.querySelectorAll('.currency-option').forEach(o => o.classList.remove('active'));
        opt.classList.add('active');
        currencyDropdownMenu.classList.remove('show');
        
        saveState();
        renderAllMetrics();
        renderActivitiesTable();
        showToast(`Currency updated to ${code} (${symbol})`);
      });
    });

    // Filter toggle
    filterToggleBtn.addEventListener('click', (e) => {
      e.stopPropagation();
      filterDropdownMenu.classList.toggle('show');
    });

    document.querySelectorAll('.filter-option:not(.export)').forEach(opt => {
      opt.addEventListener('click', () => {
        currentStatusFilter = opt.getAttribute('data-status');
        document.querySelectorAll('.filter-option').forEach(o => o.classList.remove('active'));
        opt.classList.add('active');
        filterDropdownMenu.classList.remove('show');
        renderActivitiesTable();
      });
    });

    // Close dropdowns on outside click
    document.addEventListener('click', () => {
      currencyDropdownMenu.classList.remove('show');
      filterDropdownMenu.classList.remove('show');
    });

    // Search input
    activitySearchInput.addEventListener('input', (e) => {
      currentSearchQuery = e.target.value.trim().toLowerCase();
      renderActivitiesTable();
    });

    // Checkbox selection in table
    activitiesTableBody.addEventListener('change', (e) => {
      if (e.target.classList.contains('row-checkbox')) {
        const id = e.target.getAttribute('data-id');
        const act = state.activities.find(a => a.id === id);
        if (act) {
          act.selected = e.target.checked;
          saveState();
          updateSelectedCount();
        }
      }
    });

    selectAllCheckbox.addEventListener('change', (e) => {
      const checked = e.target.checked;
      state.activities.forEach(a => a.selected = checked);
      saveState();
      renderActivitiesTable();
    });

    // Delete single activity row
    activitiesTableBody.addEventListener('click', (e) => {
      const delBtn = e.target.closest('[data-delete-id]');
      if (delBtn) {
        const id = delBtn.getAttribute('data-delete-id');
        const index = state.activities.findIndex(a => a.id === id);
        if (index !== -1) {
          const removed = state.activities.splice(index, 1)[0];
          state.spentAmount = Math.max(0, state.spentAmount - removed.price);
          saveState();
          renderAllMetrics();
          renderActivitiesTable();
          showToast(`Deleted ${removed.title}`);
        }
      }
    });

    // CSV Export
    exportCsvBtn.addEventListener('click', exportToCsv);

    // Modals
    openAddExpenseBtn.addEventListener('click', () => {
      // Set default date to now
      const now = new Date();
      now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
      expenseDateInput.value = now.toISOString().slice(0, 16);
      openModal(addExpenseModal);
    });

    closeAddExpenseModal.addEventListener('click', () => closeModal(addExpenseModal));
    cancelAddExpenseBtn.addEventListener('click', () => closeModal(addExpenseModal));

    openSetBudgetBtn.addEventListener('click', () => {
      budgetLimitInput.value = (state.spendingLimit * state.currency.rate).toFixed(0);
      openModal(setBudgetModal);
    });

    closeSetBudgetModal.addEventListener('click', () => closeModal(setBudgetModal));
    cancelSetBudgetBtn.addEventListener('click', () => closeModal(setBudgetModal));

    helpBtn.addEventListener('click', () => openModal(helpModal));
    closeHelpModal.addEventListener('click', () => closeModal(helpModal));
    okHelpBtn.addEventListener('click', () => closeModal(helpModal));

    // Form Submissions
    addExpenseForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const title = document.getElementById('expenseTitleInput').value.trim();
      const rawPrice = parseFloat(document.getElementById('expenseAmountInput').value);
      const basePrice = rawPrice / state.currency.rate;
      const category = document.getElementById('expenseCategorySelect').value;
      const status = document.getElementById('expenseStatusSelect').value;
      const dateVal = document.getElementById('expenseDateInput').value;
      
      const formattedDate = new Date(dateVal).toLocaleString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
      });

      const nextNum = (state.activities.length + 77);
      const newAct = {
        id: `INV_0000${nextNum}`,
        title,
        category,
        price: basePrice,
        status,
        date: formattedDate,
        selected: false
      };

      state.activities.unshift(newAct);
      state.spentAmount += basePrice;
      state.totalSpending += basePrice;
      saveState();

      renderAllMetrics();
      renderActivitiesTable();
      closeModal(addExpenseModal);
      addExpenseForm.reset();
      showToast(`Added: ${title} (${formatMoney(basePrice)})`);
    });

    setBudgetForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const rawLimit = parseFloat(budgetLimitInput.value);
      state.spendingLimit = rawLimit / state.currency.rate;
      saveState();
      renderAllMetrics();
      closeModal(setBudgetModal);
      showToast(`Monthly budget updated to ${formatMoney(state.spendingLimit)}`);
    });

    // Navigation Pills active toggle
    document.querySelectorAll('.center-nav-pill .nav-tab').forEach(tab => {
      tab.addEventListener('click', () => {
        document.querySelectorAll('.center-nav-pill .nav-tab').forEach(t => t.classList.remove('active'));
        tab.classList.add('active');
        showToast(`Viewing section: ${tab.textContent}`);
      });
    });

    // Dock Buttons active toggle
    document.querySelectorAll('.dock-btn[data-tab]').forEach(btn => {
      btn.addEventListener('click', () => {
        document.querySelectorAll('.dock-btn[data-tab]').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
      });
    });

    // Quick Logout mock
    document.getElementById('logoutBtn').addEventListener('click', () => {
      showToast('Logged out session.');
    });
  }

  function openModal(modal) {
    modal.classList.add('active');
    modal.setAttribute('aria-hidden', 'false');
  }

  function closeModal(modal) {
    modal.classList.remove('active');
    modal.setAttribute('aria-hidden', 'true');
  }

  // --- CSV Export Helper ---
  function exportToCsv() {
    const headers = ['Order ID', 'Activity', 'Category', 'Price (USD)', 'Status', 'Date'];
    const rows = state.activities.map(a => [
      a.id,
      `"${a.title.replace(/"/g, '""')}"`,
      a.category,
      a.price.toFixed(2),
      a.status,
      `"${a.date}"`
    ]);

    const csvContent = [headers.join(','), ...rows.map(r => r.join(','))].join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.setAttribute('href', url);
    link.setAttribute('download', `trackmint_activities_${new Date().toISOString().slice(0, 10)}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    showToast('Exported activities to CSV');
  }

  // --- Toast Notification ---
  function showToast(message) {
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.textContent = message;
    toastContainer.appendChild(toast);
    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transition = 'opacity 0.3s ease';
      setTimeout(() => toast.remove(), 300);
    }, 2800);
  }

  // Run initial setup
  init();
})();
