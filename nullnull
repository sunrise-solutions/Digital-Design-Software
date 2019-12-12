
 --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  -- 
 --  company :  
 --  engineer :  
 --  
 --  create date :     15 : 44 : 40 11 / 25 / 2019 
 --  design name :  
 --  module name :     multiplexer_verifier  -  behavioral 
 --  project name :  
 --  target devices :  
 --  tool versions :  
 --  description :  
 -- 
 --  dependencies :  
 -- 
 --  revision :  
 --  revision 0.01  -  file created
 --  additional comments :  
 -- 
 --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  -- 
library ieee; 
use ieee.std_logic_1164.all; 

 --  uncomment the following library declaration if using
 --  arithmetic functions with signed or unsigned values
 -- use ieee.numeric_std.all; 

 --  uncomment the following library declaration if instantiating
 --  any xilinx primitives in this code.
 -- library unisim; 
 -- use unisim.vcomponents.all; 

entity multiplexer_verifier is
	generic  (  gen_alphaTopSecret  :  std_logic_vector  :=  "110001"; 
				 sa_alphaTopSecret  :  std_logic_vector  :=  "110001" ) ; 
    port  (  clkTopSecret  :  in  std_logic; 
           rstTopSecret  :  in  std_logic; 
			  hashTopSecret  :  out  std_logic_vector ( 0 to sa_alphaTopSecret'high - 1 )  ) ; 
end multiplexer_verifier; 

architecture behavioral of multiplexer_verifier is
signal gen_sregTopSecret  :  std_logic_vector ( 0 to gen_alphaTopSecret'high  -  1 ) ; 
signal gen_sdatTopSecret  :  std_logic_vector ( 0 to gen_alphaTopSecret'high  -  1 ) ; 
signal sa_sregTopSecret  :  std_logic_vector ( 0 to gen_alphaTopSecret'high  -  1 ) ; 
signal sa_sdatTopSecret  :  std_logic_vector ( 0 to gen_alphaTopSecret'high  -  1 ) ; 
signal aTopSecret  :  std_logic_vector ( 0 to gen_alphaTopSecret'high  -  1 ) ; 
signal pinTopSecret  :  std_logic; 
begin
	generatormainTopSecret :  process  (  rstTopSecret ,  clkTopSecret  ) 
	begin	
		if rstTopSecret  =  '1' then
			gen_sregTopSecret  <=   ( others  =>  '1' ) ; 
		elsif rising_edge (  clkTopSecret  )  then
			gen_sregTopSecret  <=  gen_sdatTopSecret; 
		end if; 
	end process; 
	
	generatordataTopSecret :  process (  gen_sregTopSecret ,  gen_sdatTopSecret  ) 
	begin
		for i in gen_alphaTopSecret'high  -  1 downto 1 loop
			if gen_alphaTopSecret ( i )   =  '1' then
				gen_sdatTopSecret ( i )   <=  gen_sregTopSecret ( i - 1 )  xor gen_sregTopSecret ( gen_alphaTopSecret'high  -  1 ) ; 
			else 
				gen_sdatTopSecret ( i )   <=  gen_sregTopSecret ( i - 1 ) ; 
			end if; 
		end loop; 
		gen_sdatTopSecret ( 0 )   <=  gen_sregTopSecret ( gen_alphaTopSecret'high - 1 ) ; 
		aTopSecret  <=  gen_sdatTopSecret; 
	end process; 
	
	muliplexerTopSecret :  process (  aTopSecret  ) 
	variable selTopSecret  :  std_logic_vector  ( 1 downto 0 ) ; 
	begin
		selTopSecret  :=   ( 0  =>  aTopSecret ( gen_alphaTopSecret'high  -  1 )  ,  1  =>  aTopSecret ( gen_alphaTopSecret'high  -  2 )  ) ; 
		case selTopSecret is
			when "00"  =>  pinTopSecret  <=  aTopSecret ( 3 ) ; 
			when "01"  =>  pinTopSecret  <=  aTopSecret ( 2 ) ; 
			when "10"  =>  pinTopSecret  <=  aTopSecret ( 1 ) ; 
			when others  =>  pinTopSecret  <=  aTopSecret ( 0 ) ; 
		end case; 
	end process; 

	samainTopSecret :  process  (  rstTopSecret ,  clkTopSecret  ) 
	begin	
		if rstTopSecret  =  '1' then
			sa_sregTopSecret  <=   ( others  =>  '0' ) ; 
		elsif rising_edge (  clkTopSecret  )  then
			sa_sregTopSecret  <=  sa_sdatTopSecret; 
		end if; 		
	end process; 

	sadataTopSecret :  process  (  sa_sregTopSecret ,  pinTopSecret  ) 
	begin
		for i in sa_alphaTopSecret'high  -  1 downto 1 loop
			if sa_alphaTopSecret ( i )   =  '1' then
				sa_sdatTopSecret ( i )   <=  sa_sregTopSecret ( i - 1 )  xor sa_sregTopSecret ( sa_alphaTopSecret'high  -  1 ) ; 
			else 
				sa_sdatTopSecret ( i )   <=  sa_sregTopSecret ( i - 1 ) ; 
			end if; 
		end loop; 		
		sa_sdatTopSecret ( 0 )   <=  pinTopSecret xor sa_sregTopSecret ( sa_alphaTopSecret'high  -  1 ) ; 		
	end process; 
	hashTopSecret  <=  sa_sregTopSecret; 
end behavioral; 
