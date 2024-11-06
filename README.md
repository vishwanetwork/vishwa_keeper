# Babylon Blockchain Transaction Utilities

This repository provides Java utilities for interacting with the Babylon blockchain network. These utilities allow you to execute minting or burning transactions, monitor contract-based events, track staking activities, and scan for HTLC (Hashed TimeLock Contracts) transactions. Below is a description of each utility.

## 1. **BabylonScanContractUtils**
### Purpose:
This utility is designed to query Babylon blockchain transactions related to a specific contract address. It retrieves transaction details, particularly focusing on minting events and prints out relevant details such as the recipient address and amount.

### Key Features:
- Queries the Babylon blockchain for transactions involving a specific contract address.
- Identifies and prints out transactions related to minting actions.
- Provides transaction event details like action type, address, and amount.

### Usage:
Call the `getMint()` method to start scanning transactions related to the contract. It will print the mint actions, recipient addresses, and amounts involved.

### Example:
```java
BabylonScanContractUtils.getMint();
```
## 2. BabylonStakingScan
### Purpose
This utility scans Babylon blockchain transactions associated with staking, specifically looking for the `MsgCreateBTCDelegation` action. It retrieves staking information such as the delegator’s address and staked amount.

### Key Features
- Queries transactions involving staking actions, particularly the `MsgCreateBTCDelegation` message.
- Identifies and prints details of staking transactions, including the address of the staker and the staked amount.

### Usage
Call the `getStaking()` method to scan and output the details of staking transactions. This utility will print the staker address and amount staked for each matching transaction.

```java
BabylonStakingScan.getStaking();
```

## 3. BtcHTLCScan
### Purpose
This utility monitors HTLC (Hashed TimeLock Contract) transactions on the Bitcoin testnet, focusing on transactions associated with a specified lock address. It gathers relevant transaction details and filters based on HTLC lock conditions.

### Key Features
- Retrieves Bitcoin testnet transactions linked to a designated lock address via Blockstream’s API.
- Utilizes a multithreaded approach to handle up to 50 concurrent transaction checks for efficient scanning.
- Logs details of transactions that match the specified lock address, including the transaction ID and output details.

### Usage
Call `processTransactionsForBlockHeight(String height)` to scan all transactions in a specified block height. This method returns a list of matching transactions and outputs the count of total transactions, matched transactions, and any errors encountered.

```java
List<BtcTx> result = BtcHTLCScan.processTransactionsForBlockHeight("3196558");
```
## 4. BabylonCommandExecutor

### Purpose
This utility provides a streamlined method for executing Babylon blockchain transactions from Java, enabling both minting and burning of tokens by invoking `babylond` commands. The utility is designed for flexibility, allowing the user to specify transaction details such as the amount and action type (mint or burn).

### Key Features
- **Mint and Burn Functionality**: Supports both mint and burn actions, where the user specifies the amount to be minted or burned.
- **Dynamic Command Construction**: Builds and executes the `babylond` command with parameters tailored to the desired action.
- **Transaction Feedback**: Outputs transaction results to the console, including success or failure details and any potential error messages.

### Code Overview
- `executeBabylondTransaction(String amount, boolean isMint)`: This method constructs the JSON parameter based on the `isMint` flag. If `isMint` is `true`, it prepares a mint transaction; if `false`, it prepares a burn transaction.
- Command output and error messages are read and displayed in the console for transparency.

### Usage Example
```java
// Executes a mint transaction for an amount of "100"
BabylonCommandExecutor.executeBabylondTransaction("100", true);

// Executes a burn transaction for an amount of "100"
BabylonCommandExecutor.executeBabylondTransaction("100", false);
```
### Console Output
When the command executes, it outputs transaction details and any error messages to the console. The final exit code provides insight into the transaction’s success:
- **Exit Code 0**: Transaction executed successfully.
- **Non-zero Exit Code**: Transaction failed, with error details displayed in the console.

---

Together, these four utilities offer a comprehensive solution for managing transactions and contract interactions within Babylon and Bitcoin test environments. Each tool serves a specific purpose—whether it’s monitoring contracts, tracking transactions, or managing staking functions—making them valuable for efficient blockchain operations.
