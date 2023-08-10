import styled from "styled-components";

export const Table = styled.div`
  width: 100%;
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
`;

export const TableBody = styled.div`
  width: 100%;
  background-color: ${({ theme: { neutral } }) => neutral.surface.strong};
  border-bottom-left-radius: ${({ theme: { radius } }) => radius.l};
  border-bottom-right-radius: ${({ theme: { radius } }) => radius.l};
`;

export const EmptyTableBodyItem = styled.div`
  width: 100%;
  height: 96px;
  color: ${({ theme: { neutral } }) => neutral.text.weak};
  font: ${({ theme: { font } }) => font.displayMD16};
  line-height: 96px;
  text-align: center;
`;

export const TableBodyItem = styled.li`
  width: 100%;
  height: inherit;
  padding: 16px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:not(:last-child) {
    border-bottom: ${({ theme: { border, neutral } }) =>
      `${border.default} ${neutral.border.default}`};
  }
`;

export const TableHeader = styled.div`
  width: 100%;
  height: 64px;
  padding: 16px 32px;
  display: flex;
  align-items: center;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-top-left-radius: ${({ theme: { radius } }) => radius.l};
  border-top-right-radius: ${({ theme: { radius } }) => radius.l};
`;
