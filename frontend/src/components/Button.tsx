import { styled } from "styled-components";

type ButtonProps = {
  theme: Record<string, unknown>;
  $Flexible: "Flexible" | "Fixed";
  $Type: "Contained" | "Outline" | "Ghost";
  $ElementPattern: "Text Only" | "Icon+Text";
  $States: "Enabled" | "Hover" | "Press" | "Disabled" | "Selected/Active";
  $Size: "L" | "M" | "S";
  children: React.ReactNode;
  disabled: boolean;
};

function Button({
  $Flexible,
  $Type,
  $ElementPattern,
  $States,
  $Size,
  children,
  disabled,
  ...props
}: ButtonProps) {

  return (
    <Container
      $Flexible={$Flexible}
      $Type={$Type}
      $ElementPattern={$ElementPattern}
      $States={$States}
      $Size={$Size}
      disabled={disabled}
      {...props}>
      {children}
    </Container>
  );
}


const Container = styled.button<ButtonProps>`

`;

export default Button;