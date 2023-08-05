import { useNavigate } from "react-router-dom";
import { styled, useTheme } from "styled-components";
import { Icon } from "./Icon";

export function Header() {
  const navigate = useNavigate();
  const theme = useTheme();

  return (
    <Div onClick={() => navigate("/")}>
      <Anchor>
        <Icon
          name="logoLightMedium"
          fill={theme.color.neutralTextStrong}
          stroke={theme.color.neutralTextStrong}
        />
      </Anchor>
      <img
        style={{ width: "32px" }}
        src="https://avatars.githubusercontent.com/u/41321198?v=4"
      />
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: inline-flex;
  padding: 27px 80px;
  justify-content: space-between;
  align-items: center;
`;

const Anchor = styled.a`
  cursor: pointer;
`;
