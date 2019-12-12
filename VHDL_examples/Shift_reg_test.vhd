--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   12:55:48 11/24/2019
-- Design Name:   
-- Module Name:   C:/Users/Vita/Labworks/Xilinx/Labwork_4/Shift_reg_test.vhd
-- Project Name:  Labwork_4
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: Shift_reg
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY Shift_reg_test IS
END Shift_reg_test;
 
ARCHITECTURE behavior OF Shift_reg_test IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT Shift_reg
    PORT(
         Sin : IN  std_logic;
         SE : IN  std_logic;
         CLK : IN  std_logic;
         RST : IN  std_logic;
         Pout : OUT  std_logic_vector(0 to 3)
        );
    END COMPONENT;
    

   --Inputs
   signal Sin : std_logic := '0';
   signal SE : std_logic := '1';
   signal CLK : std_logic := '0';
   signal RST : std_logic := '0';

 	--Outputs
   signal Pout : std_logic_vector(0 to 3);

   -- Clock period definitions
   constant CLK_period : time := 10 ns;
 
BEGIN

   Behavioral : entity Work.Shift_reg(Behavioral)
	PORT MAP (
          Sin => Sin,
          SE => SE,
          CLK => CLK,
          RST => RST,
          Pout => Pout
        );

   reset: process
	begin
		RST <= '1';
		wait for CLK_period;
		RST <= '0';
		wait for CLK_period * 8;
	end process;
	
	CLK <= not CLK after CLK_period;
	Sin <= not Sin after CLK_period;
	SE <= not SE after CLK_period * 8;

END;
