library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity mux is
    port (    di0, di1, di2, di3: in std_logic_vector(0 to 1);
            a: in std_logic;
            e: in std_logic;
            do0, do1, do2, do3: out std_logic);
end mux;

architecture mux of mux is
begin
    process(di0, di1, di2, di3, a, e)
    begin
        if e='0' then
        case A is
            when '0' => do0 <= not di0(0) ; do1 <= not di1(0) ; do2 <= not di2(0)  ; do3 <= not di3(0);
            when '1' => do0 <= not di0(1) ; do1 <= not di1(1) ; do2 <= not di2(1)  ; do3 <= not di3(1);
            when others=>null;
        end case;
        else do0<='Z' ; do1<='Z' ; do2<='Z' ; do3<='Z';
        end if;
    end process;
end mux;
