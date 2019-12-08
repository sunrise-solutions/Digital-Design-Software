----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:44:40 11/25/2019 
-- Design Name: 
-- Module Name:    Multiplexer_verifier - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Multiplexer_verifier is
	Generic ( gen_alpha : STD_LOGIC_VECTOR := "110001";
				 sa_alpha : STD_LOGIC_VECTOR := "110001");
    Port ( CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
			  Hash : out  std_logic_vector(0 to sa_alpha'high-1));
end Multiplexer_verifier;

architecture Behavioral of Multiplexer_verifier is
signal gen_sreg : std_logic_vector(0 to gen_alpha'high - 1);
signal gen_sdat : std_logic_vector(0 to gen_alpha'high - 1);
signal sa_sreg : std_logic_vector(0 to gen_alpha'high - 1);
signal sa_sdat : std_logic_vector(0 to gen_alpha'high - 1);
signal A : std_logic_vector(0 to gen_alpha'high - 1);
signal Pin : std_logic;
begin
	GeneratorMain: process ( RST, CLK )
	begin	
		if RST = '1' then
			gen_sreg <= (others => '1');
		elsif rising_edge( CLK ) then
			gen_sreg <= gen_sdat;
		end if;
	end process;
	
	GeneratorData: process( gen_sreg, gen_sdat )
	begin
		for i in gen_alpha'high - 1 downto 1 loop
			if gen_alpha(i) = '1' then
				gen_sdat(i) <= gen_sreg(i-1) xor gen_sreg(gen_alpha'high - 1);
			else 
				gen_sdat(i) <= gen_sreg(i-1);
			end if;
		end loop;
		gen_sdat(0) <= gen_sreg(gen_alpha'high-1);
		A <= gen_sdat;
	end process;
	
	Muliplexer: process( A )
	variable sel : std_logic_vector (1 downto 0);
	begin
		sel := (0 => A(gen_alpha'high - 1), 1 => A(gen_alpha'high - 2));
		case sel is
			when "00" => Pin <= A(3);
			when "01" => Pin <= A(2);
			when "10" => Pin <= A(1);
			when others => Pin <= A(0);
		end case;
	end process;

	SAMain: process ( RST, CLK )
	begin	
		if RST = '1' then
			sa_sreg <= (others => '0');
		elsif rising_edge( CLK ) then
			sa_sreg <= sa_sdat;
		end if;		
	end process;

	SAData: process ( sa_sreg, Pin )
	begin
		for i in sa_alpha'high - 1 downto 1 loop
			if sa_alpha(i) = '1' then
				sa_sdat(i) <= sa_sreg(i-1) xor sa_sreg(sa_alpha'high - 1);
			else 
				sa_sdat(i) <= sa_sreg(i-1);
			end if;
		end loop;		
		sa_sdat(0) <= Pin xor sa_sreg(sa_alpha'high - 1);		
	end process;
	Hash <= sa_sreg;
end Behavioral;

