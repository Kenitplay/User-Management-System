import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class CRUDAppUI {
    private JFrame frame;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, emailField, phoneField, idField;
    private JButton addButton, updateButton, deleteButton, clearButton, refreshButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new CRUDAppUI().initialize();
            } catch (SQLException e) {
                showErrorDialog(null, "Database error: " + e.getMessage());
            }
        });
    }

    private void initialize() throws SQLException {
        setupFrame();
        createTablePanel();
        createFormPanel();
        createButtonPanel();
        refreshTable();
        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame("User Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        
        // Center the window
        frame.setLocationRelativeTo(null);
        
        // Add padding around the content
        ((JComponent) frame.getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void createTablePanel() {
    tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    userTable = new JTable(tableModel);
    userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    userTable.setRowHeight(36); // increased row height for bigger font
    userTable.setIntercellSpacing(new Dimension(0, 0));
    userTable.setShowGrid(false);

    // Set bigger font for table cells
    userTable.setFont(new Font("SansSerif", Font.PLAIN, 18));  // bigger cell font

    // Custom table header with bigger font
    JTableHeader header = userTable.getTableHeader();
    header.setBackground(new Color(70, 130, 180));
    header.setForeground(Color.WHITE);
    header.setFont(new Font("SansSerif", Font.BOLD, 20));  // bigger header font

    // Custom cell renderer
    userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(noFocusBorder);
            if (row % 2 == 0) {
                setBackground(new Color(240, 240, 240));
            } else {
                setBackground(new Color(230, 230, 230));
            }
            if (isSelected) {
                setBackground(new Color(173, 216, 230));
            }
            return this;
        }
    });

    userTable.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        }
    });

    JScrollPane scrollPane = new JScrollPane(userTable);
    scrollPane.setBorder(BorderFactory.createCompoundBorder(
        new EmptyBorder(5, 0, 5, 0),
        new LineBorder(new Color(200, 200, 200), 1)
    ));
    frame.add(scrollPane, BorderLayout.CENTER);
}


    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            "User Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 14),
            new Color(70, 130, 180))
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        idField = createStyledTextField(false);
        formPanel.add(idField, gbc);

        // Name Field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = createStyledTextField(true);
        formPanel.add(nameField, gbc);

        // Email Field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = createStyledTextField(true);
        formPanel.add(emailField, gbc);

        // Phone Field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = createStyledTextField(true);
        formPanel.add(phoneField, gbc);

        frame.add(formPanel, BorderLayout.NORTH);
    }

   private JTextField createStyledTextField(boolean editable) {
    JTextField field = new JTextField();
    field.setEditable(editable);

    // Increased font size from 14 to 24
    field.setFont(new Font("SansSerif", Font.PLAIN, 24));

    field.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(200, 200, 200)),
        new EmptyBorder(5, 5, 5, 5)
    ));
    field.setPreferredSize(new Dimension(200, 30));

    // Additional styling improvements
    field.setOpaque(true);
    field.setBackground(editable ? Color.WHITE : new Color(240, 240, 240));
    if (!editable) {
        field.setForeground(new Color(100, 100, 100));
    }

    return field;
}

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create buttons with consistent styling
        addButton = createStyledButton("Add", new Color(34, 139, 34));
        updateButton = createStyledButton("Update", new Color(30, 144, 255));
        deleteButton = createStyledButton("Delete", new Color(220, 20, 60));
        clearButton = createStyledButton("Clear", new Color(218, 165, 32));
        refreshButton = createStyledButton("Refresh", new Color(138, 43, 226));

        // Add action listeners
        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> {
            try {
                refreshTable();
            } catch (SQLException ex) {
                showErrorDialog(frame, ex.getMessage());
            }
        });

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(bgColor.darker(), 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void refreshTable() throws SQLException {
        tableModel.setRowCount(0);
        List<User> users = UserDAO.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
            });
        }
    }

    private void addUser() {
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                showErrorDialog(frame, "Name and Email are required fields");
                return;
            }

            User user = new User(name, email, phone);
            UserDAO.insertUser(user);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(frame, "User added successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showErrorDialog(frame, e.getMessage());
        }
    }

    private void updateUser() {
        try {
            if (idField.getText().isEmpty()) {
                showErrorDialog(frame, "Please select a user to update");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                showErrorDialog(frame, "Name and Email are required fields");
                return;
            }

            User user = new User(name, email, phone);
            user.setId(id);
            UserDAO.updateUser(user);
            refreshTable();
            JOptionPane.showMessageDialog(frame, "User updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | NumberFormatException e) {
            showErrorDialog(frame, e.getMessage());
        }
    }

    private void deleteUser() {
        try {
            if (idField.getText().isEmpty()) {
                showErrorDialog(frame, "Please select a user to delete");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to delete this user?", "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                UserDAO.deleteUser(id);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(frame, "User deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | NumberFormatException e) {
            showErrorDialog(frame, e.getMessage());
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        userTable.clearSelection();
    }

    private static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}